package com.inter.desafio.service;

import java.util.List;
import java.util.Optional;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.inter.desafio.DTO.UsuarioDTO;
import com.inter.desafio.exception.UsuarioException;
import com.inter.desafio.model.DigitoUnico;
import com.inter.desafio.model.Usuario;
import com.inter.desafio.repository.DigitoUnicoRepository;
import com.inter.desafio.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	DigitoUnicoService digitoUnicoService;
	
	@Autowired
	DigitoUnicoRepository digitoUnicoRepository;

	public Usuario buscarUsuarioPorID(Long id) throws ApplicationContextException {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		Usuario user = new Usuario();
		
		if(!usuario.isPresent()) {
			throw new UsuarioException("Usuário não encontrado.");			
		}else {
			user = usuario.get();
		}		
		return user;
	}
	
	public List<Usuario> listarUsuarios() throws ApplicationException{
		List<Usuario> listaUsuarios = usuarioRepository.findAll();
		
		if(listaUsuarios.isEmpty())
			throw new ApplicationContextException("Não existem usuários cadastrados.");
		else
			return listaUsuarios;
	}

	public Usuario salvarUsuario(Usuario usuario) throws ApplicationException {				
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
				
		if(usuarioSalvo == null)
			throw new ApplicationContextException("Erro ao salvar usuário.");
		else
			return usuarioSalvo;
	}
	
	public void excluirUsuario(UsuarioDTO usuarioDTO) throws ApplicationException{
		Optional<Usuario> usuarioSalvo = usuarioRepository.findById(usuarioDTO.getId());
		if(!usuarioSalvo.isPresent()) {
			throw new UsuarioException("Usuário não encontrado.");
		}else {
			List<DigitoUnico> listaDigitoUnico = digitoUnicoRepository.findByUsuario(usuarioSalvo.get());
			digitoUnicoService.excluirDigitoUnico(listaDigitoUnico);
			usuarioRepository.deleteById(usuarioSalvo.get().getId());
		}
	}
}
