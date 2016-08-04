package com.thecherno.rainserver;

public class RainServer {

	public static void main(String[] args) {
		Server server = new Server(8192);
		server.start();
	}

}
