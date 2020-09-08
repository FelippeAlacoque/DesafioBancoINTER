package com.inter.desafio.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inter.desafio.DTO.UsuarioDTO;
import com.inter.desafio.event.RecursoCriadoEvent;
import com.inter.desafio.model.Usuario;
import com.inter.desafio.repository.UsuarioRepository;
import com.inter.desafio.service.UsuarioService;
import com.inter.desafio.util.ConversorEntidadesUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/api/usuario")
@Api(value="API Usuários")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
		
	@GetMapping("/{codigo}")
	@ApiOperation(value="Retorna o usuário com o código informado.")
	public ResponseEntity<Usuario> buscarPeloCodigo (@PathVariable Long codigo){
		Usuario usuario = usuarioService.buscarUsuarioPorID(codigo);
		return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();	
	}

	@PostMapping
	@ApiOperation(value="Cria novo usuário.")
	public ResponseEntity<Usuario> criar(@Valid @RequestBody UsuarioDTO usuarioDTO, HttpServletResponse response) throws ApplicationException{
		Usuario usuarioSalvo = usuarioService.salvarUsuario(ConversorEntidadesUtil.convertUsuarioDtoForUsuario(usuarioDTO));
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getId()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@DeleteMapping("/usuario")
	@ApiOperation(value="Exclui um usuário pelo código informado.")
	public void excluirUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws ApplicationException {
		usuarioService.excluirUsuario(usuarioDTO);
	}

}
