package it.polimi.distsys.communication.messages;

import java.io.IOException;

import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.secure.ClientSecureLayer;

public class DONEMessage implements Message {
	private static final long serialVersionUID = 7314207659223913467L;

	@Override
	public void display() {
		Printer.printDebug(getClass(), "received");
	}

	@Override
	public void onReceive(Layer layer) {
		layer.stopReceiving();
		ClientSecureLayer sec = (ClientSecureLayer) layer;
		try {
			sec.done();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSend(Layer layer) {}

	@Override
	public Message unpack() {
		return this;
	}

}
