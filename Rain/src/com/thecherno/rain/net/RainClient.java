package com.thecherno.rain.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.thecherno.raincloud.serialization.RCDatabase;
import com.thecherno.raincloud.serialization.RCField;
import com.thecherno.raincloud.serialization.RCObject;
import com.thecherno.raincloud.serialization.RCString;

public class RainClient {

	private static final int PACKET_SIZE = 1024;
	
	private String address;
	private int port;
	
	private DatagramSocket socket;
	private InetAddress serverAddress;
	
	private Thread listenerThread;
	private boolean listening = false;
	
	private volatile boolean connected = false;
	
	public RainClient(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public boolean connect() {
		try {
			socket = new DatagramSocket();
			serverAddress = InetAddress.getByName(address);
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		
		listenerThread = new Thread(() -> { listen(); }, "RainClient-ListenerThread");
		listenerThread.start();
		
		attemptConnection();
		blockUntilConnected();
		
		return true;
	}
	
	private void attemptConnection() {
		int attempts = 0;
		
		RCDatabase database = new RCDatabase("Connection");
		
		RCObject clientInfo = new RCObject("ClientInfo");
		clientInfo.addString(RCString.Create("username", "Cherno"));
		database.addObject(clientInfo);
		
		RCObject attemptInfo = new RCObject("AttemptInfo");
		attemptInfo.addField(RCField.Integer("attempt", attempts));
//		database.addObject(attemptInfo);
		
		send(database);
	}
	
	public void listen() {
		listening = true;
		while (listening) {
			byte[] data = new byte[PACKET_SIZE];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			process(packet);
		}
	}

	public void send(RCDatabase database) {
		byte[] data = new byte[database.getSize()];
		database.getBytes(data, 0);
		send(data);
	}
	
	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void process(DatagramPacket packet) {
		// System.out.println("Packet received from " + packet.getAddress().toString() + ":" + packet.getPort());
		RCDatabase db = RCDatabase.Deserialize(packet.getData());
		if (db.getName().equals("Ack")) {
			// Pull Info
			connected = true;
		}
		
	}
	
	private void blockUntilConnected() {
		while (!connected)
			;
	}
	
}
