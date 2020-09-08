package com.inter.desafio.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.inter.desafio.model.DigitoUnico;
import com.inter.desafio.model.Usuario;
import com.inter.desafio.repository.DigitoUnicoRepository;
import com.inter.desafio.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class DigitoUnicoServiceTest {		
	
	@Autowired
	DigitoUnicoRepository digitoUnicoRepository;
	
	@Autowired
	DigitoUnicoService digitoUnicoService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Before
	public void setUpData() {		
		Usuario usuario = new Usuario();
		usuario.setNome("Usuário teste");
		usuario.setEmail("teste@mail.com");
		usuarioRepository.save(usuario);

		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		DigitoUnico digitoUnico = new DigitoUnico();
		digitoUnico.setNumeroRepeticao(4);
		digitoUnico.setPalavra("123");
		digitoUnico.setUsuario(listaUsuarios.get(0));
		digitoUnicoRepository.save(digitoUnico);		
	}
	
	@Test
	public void salvarDigitoUnico() {
		DigitoUnico digitoUnico = new DigitoUnico();
		digitoUnico.setNumeroRepeticao(4);
		digitoUnico.setPalavra("123");
		digitoUnico.setUsuario(usuarioRepository.findById(1));		
		DigitoUnico digitoUnicoSalvoBase = digitoUnicoRepository.save(digitoUnico);
		
		Optional<DigitoUnico> digitoPesquisado = digitoUnicoRepository.findById(digitoUnicoSalvoBase.getId());
		
		assertTrue(digitoUnicoSalvoBase.getId() == digitoPesquisado.get().getId());
	}

}
