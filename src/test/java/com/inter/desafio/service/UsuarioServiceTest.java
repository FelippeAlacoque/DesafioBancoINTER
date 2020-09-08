package com.inter.desafio.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.inter.desafio.DTO.UsuarioDTO;
import com.inter.desafio.model.Usuario;
import com.inter.desafio.repository.UsuarioRepository;
import com.inter.desafio.util.ConversorEntidadesUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UsuarioServiceTest {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	Usuario user = new Usuario();	
	
	@Before
	public void setUpData() {			
		user.setNome("Usuário Teste");
		user.setEmail("felippealacoque@gmail.com");
		
		usuarioRepository.save(user);		
	}
	
	@Test
	public void buscarPorIDTest() {
		Usuario usuario = usuarioService.buscarUsuarioPorID((user.getId()));
		assertEquals(usuario.getId(), user.getId());
		assertEquals(usuario.getEmail(), user.getEmail());
	}
	
	@Test
	public void listarBeneficiariosCadastrados() throws ApplicationException {
		List<Usuario> listaUsuarios = usuarioService.listarUsuarios();
		for (Usuario usuario : listaUsuarios) {
			assertEquals(user.getId(), usuario.getId());
			assertEquals(user.getNome(), usuario.getNome());
			assertEquals(user.getEmail(), usuario.getEmail());
		}
	}
	
	@Test
	public void excluirUsuario() throws ApplicationException {
		int totalUsuariosGeral, totalUsuariosAtual;
		UsuarioDTO usuarioDTO = ConversorEntidadesUtil.convertUsuarioForUsuarioDTO(user);
		
		List<Usuario> listaUsuariosGeral = usuarioService.listarUsuarios();
		totalUsuariosGeral = listaUsuariosGeral.size();
		
		usuarioService.excluirUsuario(usuarioDTO);
		
		List<Usuario> listaUsuarioAtualizada = usuarioService.listarUsuarios();
		totalUsuariosAtual = listaUsuarioAtualizada.size();
		
		assertTrue(totalUsuariosGeral > totalUsuariosAtual);
	}
}
