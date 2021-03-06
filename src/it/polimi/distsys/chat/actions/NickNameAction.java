package it.polimi.distsys.chat.actions;

import it.polimi.distsys.chat.ChatFrame;
import it.polimi.distsys.chat.Peer;
import it.polimi.distsys.chat.Printer;

public class NickNameAction implements Action {
	private ChatFrame chatFrame = ChatFrame.get();

	@Override
	public void execute(Peer peer, String... params) {
		String message = "";
		String param = "";
		for (String p : params) {
			param += p;
		}
		if (param.isEmpty()) {
			message = ">>> cannot set an empty nickname";
		} else {
			param = param.replace(" ", "_");
			message = ">>> nickname changed to " + param;
			peer.setNickname(param);
		}
		chatFrame.setNickname(peer.getNickname());
		Printer.print(message);
	}

}
