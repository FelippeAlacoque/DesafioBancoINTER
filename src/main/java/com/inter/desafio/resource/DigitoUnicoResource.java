package com.inter.desafio.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inter.desafio.event.RecursoCriadoEvent;
import com.inter.desafio.model.DigitoUnico;
import com.inter.desafio.model.Usuario;
import com.inter.desafio.model.WrapRequestBody;
import com.inter.desafio.repository.DigitoUnicoRepository;
import com.inter.desafio.repository.UsuarioRepository;
import com.inter.desafio.service.DigitoUnicoService;
import com.inter.desafio.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/api/digito_unico")
@Api(value="API dígito único")
public class DigitoUnicoResource {
	
	@Autowired
	private DigitoUnicoService digitoUnicoService;
	
	@Autowired
	private DigitoUnicoRepository digitounicorepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
		
	@PostMapping
	@ApiOperation(value="Calcula dígito único de um inteiro")
	@Cacheable(value = "digitoUnico", cacheManager="10" )  
	public ResponseEntity<DigitoUnico> calcularDigitoUnico(@Valid @RequestBody WrapRequestBody wrapRequestBody, HttpServletResponse response){		
		DigitoUnico digUnSalvo = new DigitoUnico();
		DigitoUnico tempDigUnico = new DigitoUnico(wrapRequestBody.getNumeroRepeticao(),wrapRequestBody.getPalavra());
		Usuario usuTemp = usuarioService.buscarUsuarioPorID(wrapRequestBody.getIdUsuario());				
		Integer result = digitoUnicoService.resolverDigitoUnico(tempDigUnico);		
		
		tempDigUnico.setResultado(result);
		tempDigUnico.setUsuario(usuTemp);

		try {
			digUnSalvo = digitounicorepository.save(tempDigUnico);
		} catch (Exception e) {
			e.getMessage();
		}	
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, digUnSalvo.getId()));
		
		List<DigitoUnico> listaDigitosDoUsuario = digitoUnicoService.buscarDigitosCalculadosPorUsuario(usuTemp.getId());
		usuTemp.setDigitoUnicoList(listaDigitosDoUsuario);
		usuarioRepository.save(usuTemp);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(digUnSalvo);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="Buscar lista de cálculos por usuário.")
	public List<DigitoUnico> buscarListaDeCalculosPorUsuario(@PathVariable Long id){						
		List<DigitoUnico> listaDigitoUnico = digitoUnicoService.buscarDigitosCalculadosPorUsuario(id);		
		return (List<DigitoUnico>) listaDigitoUnico;		
	}

}
