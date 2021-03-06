package it.polimi.distsys.communication.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitState implements ClientState {
	private ClientSecureLayer layer;
	private List<Message> sendingQueue = new ArrayList<Message>();

	public InitState(ClientSecureLayer layer) {
		super();
		this.layer = layer;
	}

	@Override
	public void keysReceived() throws IOException {
		layer.sendACK();
		layer.setState(new ReadyState(layer, sendingQueue));
	}

	@Override
	public void stop() throws IOException {
		layer.sendACK();
	}

	@Override
	public void done() throws IOException {
		layer.sendACK();
	}

	@Override
	public boolean send(Message msg) {
		sendingQueue.add(msg);
		return false;
	}

	@Override
	public boolean receive(Message msg) throws IOException {
		return false;
	}
}
