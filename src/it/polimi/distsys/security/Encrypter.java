package it.polimi.distsys.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Encrypter {

    private SecretKey dek;

    public Encrypter(SecretKey dek) {
	this.dek = dek;
    }

    public void updateKey(SecretKey newKey) {
	this.dek = newKey;
    }

    public byte[] encrypt(String string) {
	Cipher cipher;
	byte[] encrypted = null;
	try {
	    cipher = Cipher.getInstance("DES");
	    cipher.init(Cipher.ENCRYPT_MODE, this.dek);
	    encrypted = cipher.doFinal(string.getBytes());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return encrypted;
    }

}
