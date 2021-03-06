package it.polimi.distsys.communication.secure;

import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;

public interface ClientState {
	
	public void keysReceived() throws IOException;
	public void stop() throws IOException;
	public void done() throws IOException;
	public boolean send(Message msg);
	public boolean receive(Message msg) throws IOException;
}
