package org.hni.security.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.hni.security.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class EncryptionService {
	private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);
	private KeyStore keyStore;
	
	private String keyStorePassword;
	private String ivString;
	
	public EncryptionService(@Value("#{hniProperties['keystore.location']}") String keyStoreLocation
			,@Value("#{hniProperties['keystore.password']}") String keyStorePassword
			,@Value("#{hniProperties['keystore.iv']}") String ivString) throws EncryptionException {
		this.keyStorePassword = keyStorePassword;
		this.ivString = ivString;
		this.keyStore = getKeyStore(keyStoreLocation);
	}
	
	private KeyStore getKeyStore(String keyStoreLocation) throws EncryptionException {
		FileInputStream is;
		try {
			is = new FileInputStream(keyStoreLocation);
	        KeyStore keystore = KeyStore.getInstance("JCEKS");
	        keystore.load(is, keyStorePassword.toCharArray());
	        return keystore;
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new EncryptionException(e.getMessage(), e);
		}
	}

	public String encrypt(String keyName, String value) throws EncryptionException {
		try {
			Key key = keyStore.getKey(keyName, keyStorePassword.toCharArray());
			return encrypt(key.getEncoded(), Base64.getDecoder().decode(ivString), value);
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new EncryptionException(e.getMessage(), e);
		}		
	}
	
	public String decrypt(String keyName, String value) throws EncryptionException {
		try {
			Key key = keyStore.getKey(keyName, keyStorePassword.toCharArray());
			return decrypt(key.getEncoded(), Base64.getDecoder().decode(ivString), value);
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new EncryptionException(e.getMessage(), e);
		}				
	}
	
    public static String encrypt(byte[] key, byte[] iv, String value) throws EncryptionException {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return new String(Base64.getEncoder().encode(encrypted));
        } catch (Exception e) {
            logger.error("Unable to encrypt:"+e.getMessage());
            throw new EncryptionException("Unable to encrypt:"+e.getMessage(), e);
        }
    }

    public static String decrypt(byte[] key, byte[] iv, String encrypted) throws EncryptionException {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (Exception e) {
        	 logger.error("Unable to decrypt:"+e.getMessage());
        	 throw new EncryptionException("Unable to decrypt:"+e.getMessage(), e);
        }
    }

}
