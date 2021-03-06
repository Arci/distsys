package it.polimi.distsys.communication.secure;

import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.communication.Layer;
import it.polimi.distsys.communication.messages.EncryptedMessage;
import it.polimi.distsys.communication.messages.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public abstract class SecureLayer extends Layer {
	protected Encrypter enc;
	protected Decrypter dec;
	public static boolean DEBUG;

	@Override
	public List<Message> processOnReceive(Message msg) throws IOException {
		EncryptedMessage encrypted = (EncryptedMessage) msg;
		Message m = null;
		try {
			m = dec.decryptMsg(encrypted);
		} catch (ClassNotFoundException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return new ArrayList<Message>(Arrays.asList(m));
	}

	@Override
	public List<Message> processOnSend(Message msg) {
		Message encrypted = null;
		try {
			encrypted = enc.encryptMsg(msg);
		} catch (IllegalBlockSizeException | IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<Message>(Arrays.asList(encrypted));
	}

	public boolean isForMe(UUID id) {
		return Peer.ID.equals(id);
	}
}
