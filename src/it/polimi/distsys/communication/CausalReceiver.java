package it.polimi.distsys.communication;

import java.util.List;

public class CausalReceiver implements Receiver {
	private Receiver receiver;

	public CausalReceiver(Receiver receiver) {
		super();
		this.receiver = receiver;
	}

	@Override
	public List<Message> receive() {
		// TODO Auto-generated method stub
		System.out.println(getClass().getName() + " invoked!");
		return receiver.receive();
	}

}
