package it.polimi.distsys.peers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Receptionist implements Runnable {
	ServerSocket in;
	Peer peer;

	public Receptionist(ServerSocket in, Peer peer) {
		super();
		this.in = in;
		this.peer = peer;
	}

	@Override
	public void run() {

		while (true) {
			try {
				Socket socket = in.accept();
				System.out.println("Received connection from "
						+ socket.getInetAddress() + ":" + socket.getPort());
				peer.join(new Host(socket, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}