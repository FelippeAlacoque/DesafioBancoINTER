package com.inter.desafio.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.inter.desafio.DTO.UsuarioDTO;
import com.inter.desafio.model.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CriptografiaRSAUtilTest {
	
	Usuario usuario = new Usuario();
	
	@Before
	public void setUpData() {
		usuario.setNome("Felippe Alacoque");
		usuario.setEmail("felippealacoque@gmail.com");
	}
	
	@Test
	public void criptografiaTeste() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, IOException, ClassNotFoundException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		CriptografiaRSA cripto = new CriptografiaRSA();
		cripto.geraParChaves(new File ("chave.publica"), new File ("chave.privada"));
		
		//Cifrando a mensagem
        byte[] textoClaro = usuario.getNome().getBytes("ISO-8859-1");
        PublicKey pub = cripto.carregaChavePublica(new File ("chave.publica"));
        byte[][] cifrado = cripto.cifrador(pub, textoClaro);
        
        // Decifrando a mensagem 
        PrivateKey pvk = cripto.carregaChavePrivada(new File ("chave.privada"));
        byte[] decifrado = cripto.decifra (pvk, cifrado[0], cifrado[1]);
        System.out.println("Mensagem pura:" + new String (textoClaro, "ISO-8859-1") );
        System.out.println (new String (decifrado, "ISO-8859-1"));
        
        assertEquals("Verificando criptografia: ",(new String (textoClaro, "ISO-8859-1").equalsIgnoreCase(new String (decifrado, "ISO-8859-1"))));
	}
	
}
