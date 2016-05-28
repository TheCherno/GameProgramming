package com.thecherno.rain.server;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1)
			return; // TODO: Error message
		
		int port = Integer.parseInt(args[0]);
		RainServer server = new RainServer(port);
		server.start();
	}

}
