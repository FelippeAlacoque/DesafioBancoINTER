package com.inter.desafio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.inter.desafio.model.DigitoUnico;
import com.inter.desafio.model.Usuario;
import com.inter.desafio.model.WrapRequestBody;
import com.inter.desafio.resource.DigitoUnicoResource;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CacheTest {
	
	WrapRequestBody wrap = new WrapRequestBody();	
	Usuario usuario = new Usuario();
	
	@Autowired
	DigitoUnicoResource digitoUnicoResource;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Before
	public void setUpData() {		
		usuario.setNome("Teste Usuario Cache");
		usuario.setEmail("usuarioTesteCache@gmail.com");
		
		try {
			usuario = usuarioService.salvarUsuario(usuario);
		} catch (ApplicationException e) {
			e.getMessage();
		}
		
		wrap.setIdUsuario(usuario.getId());
		wrap.setNumeroRepeticao(4);
		wrap.setPalavra("123");
	}
	
	@Test
	public void chacheTest() {
		ResponseEntity<DigitoUnico> digito1 = digitoUnicoResource.calcularDigitoUnico(wrap, null);
		ResponseEntity<DigitoUnico> digito2 = digitoUnicoResource.calcularDigitoUnico(wrap, null);
		
		assertEquals(digito1, digito2);
	}

}
