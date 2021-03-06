package it.polimi.distsys.communication.messages;

import it.polimi.distsys.chat.Printer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.reliable.SequenceNumber;

public class SequenceNumberMessage implements MessageDecorator {
	private static final long serialVersionUID = -6506203353941938533L;
	private Message message;
	private SequenceNumber sn;
	
	public SequenceNumberMessage(SequenceNumber sn, Message message) {
		super();
		this.message = message;
		this.sn = sn;
	}

	@Override
	public void display() {
		Printer.printDebug(getClass(), this.toString());
	}
	
	@Override
	public Message unpack() {
		return message;
	}
	
	@Override
	public String toString() {
		return "[SN: " + sn.toString() + ", " + message.getClass().getSimpleName() + "]";
	}
	
	public SequenceNumber getSn() {
		return sn;
	}

	@Override
	public void onSend(Layer layer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onReceive(Layer layer) {
		//does nothing
	}

}
