package it.polimi.distsys.communication.components;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.KeyGenerator;

public class FlatTable implements Iterable<UUID> {
	public final static int MAX_GROUP_SIZE = 8;
	public final static int BITS = (int) Math.ceil(Math.log(MAX_GROUP_SIZE)
			/ Math.log(2));
	private Key[] zeros;
	private Key[] ones;
	private Key dek;
	private List<UUID> members;
	private KeyGenerator keygen;

	public FlatTable() {
		Printer.printDebug(getClass(), "FlatTable initialized: max group size: " + MAX_GROUP_SIZE + ", bits: " + BITS);
		zeros = new Key[BITS];
		ones = new Key[BITS];
		members = new ArrayList<UUID>();
		try {
			keygen = KeyGenerator.getInstance(MessageDecrypter.ALGORITHM);
			keygen.init(new SecureRandom());
			dek = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Key[] getKEKs(UUID memberID) throws TableException {
		Key[] keks = new Key[BITS];
		int[] bits = getBits(memberID);
		for (int i = 0; i < bits.length; i++) {
			if (Integer.valueOf(bits[i]) == 1) {
				keks[i] = ones[i];
			} else {
				keks[i] = zeros[i];
			}
		}
		return keks;
	}

	public Key[] updateKEKs(UUID memberID) throws TableException {
		Key[] keks = new Key[BITS];
		int[] bits = getBits(memberID);
		for (int i = 0; i < bits.length; i++) {
			if (Integer.valueOf(bits[i]) == 1) {
				ones[i] = keygen.generateKey();
				keks[i] = ones[i];
			} else {
				zeros[i] = keygen.generateKey();
				keks[i] = zeros[i];
			}
		}
		return keks;
	}

	public Key[] join(UUID memberID) throws TableException {
		if (members.size() + 1 > MAX_GROUP_SIZE) {
			throw new TableException("Group size exceeded!");
		}
		members.add(memberID);

		return getKEKs(memberID);
	}

	public Key[] leave(UUID memberID) throws TableException {
		if (!members.contains(memberID)) {
			throw new TableException("The given UUID isn't in members!");
		}
		Key[] keks = getKEKs(memberID);
		members.remove(memberID);
		return keks;
	}

	public Key refreshDEK() {
		dek = keygen.generateKey();
		return dek;
	}

	public Key getDEK() {
		return dek;
	}

	public Map<UUID, List<Integer>> getInterested(UUID id)
			throws TableException {
		Map<UUID, List<Integer>> interested = new HashMap<UUID, List<Integer>>();
		int[] bits = getBits(id);

		for (UUID member : members) {
			int[] otherBits = getBits(member);
			for (int i = 0; i < bits.length; i++) {
				if (bits[i] == otherBits[i]) {
					if (interested.get(member) == null) {
						interested.put(member, new ArrayList<Integer>());
					}
					interested.get(member).add(i);
					break;
				}
			}
		}

		return interested;
	}

	private int[] getBits(UUID memberID) throws TableException {
		if (!members.contains(memberID)) {
			throw new TableException("The given UUID isn't in members!");
		}
		Integer ID = members.indexOf(memberID);
		String base2 = Integer.toBinaryString(ID);
		while (base2.length() < BITS) {
			base2 = "0" + base2;
		}

		char[] splitted = base2.toCharArray();
		int[] toReturn = new int[splitted.length];

		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = Integer.valueOf(splitted[i]);
		}
		
		return toReturn;
	}

	@Override
	public Iterator<UUID> iterator() {
		return members.iterator();
	}

	@Override
	public String toString() {
		String string = "";

		for (UUID id : members) {
			string += "\t" + id.toString().substring(0, 4) + "#"
					+ members.indexOf(id);
		}
		string += "\n0:";
		for (Key k : zeros) {
			string += "\t" + k.toString().substring(0, 4);
		}
		string += "\n1:";
		for (Key k : ones) {
			string += "\t" + k.toString().substring(0, 4);
		}
		return string;
	}
}
