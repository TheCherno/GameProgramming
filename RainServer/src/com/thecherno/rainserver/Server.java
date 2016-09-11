package com.thecherno.rainserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import com.thecherno.raincloud.serialization.RCDatabase;
import com.thecherno.raincloud.serialization.RCField;
import com.thecherno.raincloud.serialization.RCObject;
import com.thecherno.raincloud.serialization.Type;

public class Server {

	private int port;
	private Thread listenThread;
	private boolean listening = false;
	private DatagramSocket socket;
	
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];

	public Server(int port) {
		this.port = port;
	}
	
	public void start() {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		
		listening = true;
		
		listenThread = new Thread(() -> listen(), "RainCloudServer-ListenThread");
		listenThread.start();
	}
	
	private void listen() {
		while (listening) {
			DatagramPacket packet = new DatagramPacket(receivedDataBuffer, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			process(packet);
		}
	}
	
	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		dump(packet);
		
		if (new String(data, 0, 4).equals("RCDB")) {
			RCDatabase database = RCDatabase.Deserialize(data);
			process(database);
		} else {
			switch (data[0]) {
			case 1:
				// Connection packet
				break;
			case 2:
				// Ping packet
				break;
			case 3:
				// Login attempt packet
				break;
			}
		}
	}
	
	private void process(RCDatabase database) {
		System.out.println("Received database!");
		dump(database);
	}
	
	public void send(byte[] data, InetAddress address, int port) { 
		assert(socket.isConnected());
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void dump(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		
		System.out.println("----------------------------------------");
		System.out.println("PACKET:");
		System.out.println("\t" + address.getHostAddress() + ":" + port);
		System.out.println();
		System.out.println("\tContents:");
		System.out.println("\t\t" + new String(data));
		System.out.println("----------------------------------------");
	}
	
	private void dump(RCDatabase database) {
		System.out.println("----------------------------------------");
		System.out.println("               RCDatabase               ");
		System.out.println("----------------------------------------");
		System.out.println("Name: " + database.getName());
		System.out.println("Size: " + database.getSize());
		System.out.println("Object Count: " + database.objects.size());
		System.out.println();
		for (RCObject object : database.objects) {
			System.out.println("\tObject:");
			System.out.println("\tName: " + object.getName());
			System.out.println("\tSize: " + object.getSize());
			System.out.println("\tField Count: " + object.fields.size());
			for (RCField field : object.fields) {
				System.out.println("\t\tField:");
				System.out.println("\t\tName: " + field.getName());
				System.out.println("\t\tSize: " + field.getSize());
				String data = "";
				switch (field.type) {
				case Type.BYTE:
					data += field.getByte();
					break;
				case Type.SHORT:
					data += field.getShort();
					break;
				case Type.CHAR:
					data += field.getChar();
					break;
				case Type.INTEGER:
					data += field.getInt();
					break;
				case Type.LONG:
					data += field.getLong();
					break;
				case Type.FLOAT:
					data += field.getFloat();
					break;
				case Type.DOUBLE:
					data += field.getDouble();
					break;
				case Type.BOOLEAN:
					data += field.getBoolean();
					break;
				}
				System.out.println("\t\tData: " + data);
				System.out.println();
			}
			System.out.println();
		}
		System.out.println("----------------------------------------");
	}
		
}
