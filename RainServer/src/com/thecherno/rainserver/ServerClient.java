package com.thecherno.rainserver;

import java.net.InetAddress;

public class ServerClient {

	public int userID;
	public InetAddress address;
	public int port;
	public boolean status = false; // is connected
	
	private static int userIDCounter = 1;
	
	public ServerClient(InetAddress address, int port) {
		userID = userIDCounter++;
		this.address = address;
		this.port = port;
		status = true;
	}
	
	public int hashCode() {
		return userID;
	}
	
	
}
