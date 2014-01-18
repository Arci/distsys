package it.polimi.distsys.communication.layers.secure;

import it.polimi.distsys.communication.components.TableException;
import it.polimi.distsys.communication.messages.STOPMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class NormalState implements ServerState {
	private ServerSecureLayer layer;
	private List<UUID> members = new ArrayList<UUID>();
	private List<UUID> joiners = new ArrayList<UUID>();
	private List<UUID> leavers = new ArrayList<UUID>();

	public NormalState(ServerSecureLayer layer) {
		super();
		this.layer = layer;
		Iterator<UUID> itr = layer.getTable().iterator();
		while(itr.hasNext()){
			members.add(itr.next());
		}
	}

	@Override
	public void join(UUID id) throws IOException, TableException {
		members.add(id);
		joiners.add(id);
		layer.sendDown(new STOPMessage());
		layer.setState(new SendingKeysState(layer, members, joiners, leavers));
	}

	@Override
	public void leave(UUID id) throws TableException, IOException {
		members.remove(id);
		if(members.isEmpty()){
			layer.getTable().leave(id);
			return;
		}
		leavers.add(id);
		layer.sendDown(new STOPMessage());
		layer.setState(new SendingKeysState(layer, members, joiners, leavers));
	}

	@Override
	public void ACKReceived(UUID id) {}

}
