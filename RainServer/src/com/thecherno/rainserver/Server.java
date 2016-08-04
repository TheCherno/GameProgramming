package com.thecherno.rainserver;

import java.net.DatagramPacket;

public class Server {

	private int port;
	private Thread listenThread;
	private boolean listening = false;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void start() {
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
	
	public void send(byte[] data) { 
		
	}
		
}
