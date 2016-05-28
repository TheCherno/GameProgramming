package com.thecherno.rain.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.thecherno.raincloud.serialization.RCDatabase;
import com.thecherno.raincloud.serialization.RCField;
import com.thecherno.raincloud.serialization.RCObject;
import com.thecherno.raincloud.serialization.Type;

public class RainServer {

	private static final int PACKET_SIZE = 1024;
	
	private int port;
	private DatagramSocket socket;
	private Thread listenerThread;
	
	private boolean listening = false;

	public RainServer(int port) {
		this.port = port;
	}

	public void start() {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		System.out.println("Started RainServer on port " + port + "...");
		
		listenerThread = new Thread(() -> { listen(); }, "RainServer-ListenerThread");
		listenerThread.start();
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
	
	private void process(DatagramPacket packet) {
		System.out.println("Packet received from " + packet.getAddress().toString() + ":" + packet.getPort());
		RCDatabase db = RCDatabase.Deserialize(packet.getData());
		dumpDatabase(db);
		
		if (db.getName().equals("Connection")) {
			sendAckPacket(packet.getAddress(), packet.getPort());
			// TODO: Add client to list of clients (and start maintaining (i.e. pinging) this client)
		}
	}
	
	private void sendAckPacket(InetAddress address, int port) {
		RCDatabase db = new RCDatabase("Ack");
		send(db, address, port);
	}
	
	public void send(RCDatabase database, InetAddress address, int port) {
		byte[] data = new byte[database.getSize()];
		database.getBytes(data, 0);
		send(data, address, port);
	}
	
	public void send(byte[] data, InetAddress address, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void dumpDatabase(RCDatabase database) {
		System.out.println("--------------------");
		System.out.println(" RainCloud Database:");
		System.out.println("--------------------");
		System.out.println("Name: " + database.getName()); 
		System.out.println("Size: " + database.getSize() + " bytes"); 
		System.out.println("Object count: " + database.objects.size()); 
		System.out.println();
		if (database.objects.size() > 0)
		{
			System.out.println("Objects:");
			for (RCObject obj : database.objects) {
				System.out.println("\tObject name: " + obj.getName());
				System.out.println("\tObject size: " + obj.getSize());
				System.out.println("\tObject field count: " + obj.fields.size());
				System.out.println();
				if (obj.fields.size() == 0)
					continue;
				System.out.println("\tFields: ");
				for (RCField field : obj.fields) {
					System.out.println("\t\tField name: " + field.getName());
					System.out.println("\t\tField size: " + field.getSize() + " bytes");
					System.out.println("\t\tField type: " + Type.typeToString(field.type));
					String rawData = "";
					for (int i = 0; i < Type.getSize(field.type); i++)
						rawData += field.data[i];
					System.out.println("\t\tRaw data: " + rawData);
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
			}
			System.out.println("--------------------");
		}
	}

}
