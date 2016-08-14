package com.thecherno.rainserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

	private int port;
	private Thread listenThread;
	private boolean listening = false;
	private DatagramSocket socket;
	
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
		
		listenThread = new Thread(() -> listen());
		listenThread.start();
	}
	
	private void listen() {
		while (listening) {
			
		}
	}
	
	private void process(DatagramPacket packet) {
		
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
		
}
