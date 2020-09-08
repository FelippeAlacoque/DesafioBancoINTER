package com.inter.desafio.resource;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inter.desafio.model.Usuario;
import com.inter.desafio.util.CriptografiaRSA;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/api/criptografia")
@Api(value="API criptografia RSA")
public class CriptografiaResource {
	
	@PostMapping
	@ApiOperation(value="Criptografar usuario com RSA(2048)")
	public void criptografar(@Valid @RequestBody Usuario usuario) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, IOException, ClassNotFoundException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		CriptografiaRSA cripto = new CriptografiaRSA();
		cripto.geraParChaves(new File ("chave.publica"), new File ("chave.privada"));
		
		//Cifrando a mensagem
        byte[] textoClaro = usuario.getNome().getBytes("ISO-8859-1");
        PublicKey pub = cripto.carregaChavePublica(new File ("chave.publica"));
        byte[][] cifrado = cripto.cifrador(pub, textoClaro);
        
        //printHex (cifrado[0]);
        //printHex (cifrado[1]);
        
        // Decifrando a mensagem 
        PrivateKey pvk = cripto.carregaChavePrivada(new File ("chave.privada"));
        byte[] decifrado = cripto.decifra (pvk, cifrado[0], cifrado[1]);
        System.out.println (new String (textoClaro, "ISO-8859-1"));
	}
	
	/*
	@PostMapping
	@ApiOperation(value="Criptografar usuario com RSA(2048)")
	public ResponseEntity<Usuario> criar(@RequestBody @Valid Usuario usuario) throws ApplicationException{
		return null;
		
	}
	 */
}
