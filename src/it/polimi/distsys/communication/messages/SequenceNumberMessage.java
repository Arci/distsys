package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.peers.Host;

public class SequenceNumberMessage implements MessageDecorator {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6506203353941938533L;
	private Message message;
	int id;

	public SequenceNumberMessage(Message message, int id) {
		super();
		this.message = message;
		this.id = id;
	}

	@Override
	public void display() {
		
		System.out.println("display on " + getClass().getCanonicalName());
	}

	@Override
	public Message unpack() {
		return message;
	}

	@Override
	public String toString() {
		return "SequenceNumber [message=" + message + ", id=" + id + "]";
	}

	@Override
	public void execute(Peer receiver, Host sender) {
		// TODO Auto-generated method stub
		
	}

	

}
