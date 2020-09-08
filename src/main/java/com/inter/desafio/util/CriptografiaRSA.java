package com.inter.desafio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CriptografiaRSA {
	
	private static final int RSAKEYSIZE = 1024;

	public PrivateKey carregaChavePrivada (File fPvk) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPvk));
        PrivateKey ret = (PrivateKey) ois.readObject();
        ois.close();
        return ret;
    }
	
	 public PublicKey carregaChavePublica (File fPub) throws IOException, ClassNotFoundException {
	        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPub));
	        PublicKey ret = (PublicKey) ois.readObject();
	        ois.close();
	        return ret;
	    }
	 
	 public byte[][] cifrador (PublicKey pub, byte[] texto) throws NoSuchAlgorithmException, NoSuchPaddingException, 
	  InvalidKeyException, InvalidAlgorithmParameterException, 
	  IllegalBlockSizeException, BadPaddingException{

		byte[] infoCifrada = null;
		byte[] chaveCifrada = null;
		
		//chave simétrica de 128 bits com AES
		KeyGenerator kg = KeyGenerator.getInstance ("AES");
		kg.init (128);
		SecretKey sk = kg.generateKey ();
		byte[] chave = sk.getEncoded();
		
		//cifra a informação com a chave simétrica gerada
		Cipher aescf = Cipher.getInstance ("AES/CBC/PKCS5Padding");
		IvParameterSpec ivspec = new IvParameterSpec (new byte[16]);
		aescf.init (Cipher.ENCRYPT_MODE, new SecretKeySpec (chave, "AES"), ivspec);
		infoCifrada = aescf.doFinal (texto);
		
		//cifranco a chave simétrica com a chave pública
		Cipher rsacf = Cipher.getInstance ("RSA");
		rsacf.init (Cipher.ENCRYPT_MODE, pub);
		chaveCifrada = rsacf.doFinal (chave);
		
		return new byte[][] { infoCifrada, chaveCifrada };
	}
	 
	 public byte[] decifra (PrivateKey pvk, byte[] textoCifrado, byte[] chaveCifrada) throws NoSuchAlgorithmException, 
	    NoSuchPaddingException, 
	    InvalidKeyException, 
	    IllegalBlockSizeException,
	    BadPaddingException, 
	    InvalidAlgorithmParameterException  {

		byte[] textoDecifrado = null;
		//Decifra a chave simétrica com a chave privada
		Cipher rsacf = Cipher.getInstance ("RSA");
		rsacf.init (Cipher.DECRYPT_MODE, pvk);
		byte[] chaveDecifrada = rsacf.doFinal (chaveCifrada);
		
		//Decifra o texto com a chave simétrica decifrada
		Cipher aescf = Cipher.getInstance ("AES/CBC/PKCS5Padding");
		IvParameterSpec ivspec = new IvParameterSpec (new byte[16]);
		aescf.init (Cipher.DECRYPT_MODE, new SecretKeySpec (chaveDecifrada, "AES"), ivspec);
		textoDecifrado = aescf.doFinal (textoCifrado);
		
		return textoDecifrado;
	}
	
	 public void geraParChaves(File fPub, File fPvk) throws IOException, NoSuchAlgorithmException,
	 														InvalidAlgorithmParameterException, 
	 														CertificateException, KeyStoreException {
		            
        KeyPairGenerator kpg = KeyPairGenerator.getInstance ("RSA");
        kpg.initialize (new RSAKeyGenParameterSpec(RSAKEYSIZE, RSAKeyGenParameterSpec.F4));
        KeyPair kpr = kpg.generateKeyPair ();
        PrivateKey priv = kpr.getPrivate();        
        PublicKey pub = kpr.getPublic();
        
        //-- Gravando a chave pública em formato serializado
        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fPub));
        oos.writeObject (pub);
        oos.close();
        
        //-- Gravando a chave privada em formato serializado
        //-- Não é a melhor forma (deveria ser guardada em um keystore, e protegida por senha), 
        //-- mas isto é só um exemplo
        oos = new ObjectOutputStream (new FileOutputStream (fPvk));
        oos.writeObject (priv);
        oos.close();
		        
	}
	
	
	
	/*
	
	private static PublicKey publicKey;
    private static PrivateKey privateKey;    
	
	public static final String ALGORITMO = "RSA";
	
	public CriptografiaRSA(PublicKey publicKey, PrivateKey privateKey) {
	        CriptografiaRSA.publicKey = publicKey;
	        CriptografiaRSA.privateKey = privateKey;
	}

	public static KeyPair gerarChaveRSA() throws NoSuchAlgorithmException {		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITMO);
		keyPairGen.initialize(2048);		
		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		return keyPair;		
	}
	
	public static String criptografar(String info, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(ALGORITMO);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		return new String(cipher.doFinal(info.getBytes()));
	}
	
	public static String descriptografar(String info, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {		
		Cipher cipher = Cipher.getInstance(ALGORITMO);		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return new String(cipher.doFinal(info.getBytes()));
	}
	*/
}
