package com.inter.desafio.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inter.desafio.exception.DigitoUnicoException;
import com.inter.desafio.model.DigitoUnico;
import com.inter.desafio.model.Usuario;
import com.inter.desafio.repository.DigitoUnicoRepository;

@Service
public class DigitoUnicoService {
	
	@Autowired
	DigitoUnicoRepository digitoUnicoRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	private void validarDadosEntrada(DigitoUnico digitoUnico) {
		if (digitoUnico != null) {
			Pattern p = Pattern.compile("[0-9]");
			Matcher m = p.matcher(digitoUnico.getPalavra());
			boolean valido = m.find();
					
			if(digitoUnico.getNumeroRepeticao() != null && (digitoUnico.getNumeroRepeticao() <1 || digitoUnico.getNumeroRepeticao() > Math.pow(10, 5))) {
				throw new DigitoUnicoException("O número de repetições deverá estar entre 1 e 100.000");
			}
			if(digitoUnico.getPalavra() != null && !valido){
				throw new DigitoUnicoException("A palavra informada deverá conter apenas número inteiros enre 1 e 10.000.00");
			}				
		}
	}

	public Integer resolverDigitoUnico (DigitoUnico digitoUnico) {
		Integer result = null;		
		validarDadosEntrada(digitoUnico);
		
		if(digitoUnico.getNumeroRepeticao() > 0 && !digitoUnico.getPalavra().isEmpty()) 
			result = concatenarPalavra(digitoUnico.getPalavra(), digitoUnico.getNumeroRepeticao());
		else if(!digitoUnico.getPalavra().isEmpty()) 
			result = calcularDigitoUnico(digitoUnico.getPalavra());		
		
		return result;
	}

	public Integer concatenarPalavra(String palavra, Integer repeticao) {
		String concat ="";
		for(int i = 0; i < repeticao; i++) {
			concat += palavra;
		}
		return calcularDigitoUnico(concat);
	}
	
	public Integer calcularDigitoUnico(String palavra) {
		Integer resultado, soma = 0;

		if(palavra.length() == 1) {
			resultado = Integer.parseInt(palavra);
			return resultado;
		}
		
		for (char numTemp: palavra.toCharArray()) {
            soma += Integer.parseInt(Character.toString(numTemp));
        }
		
		if(soma.toString().length() > 1)
			return calcularDigitoUnico(soma.toString());
		
		return soma;
	}

	public void excluirDigitoUnico(List<DigitoUnico> listaDigitoUnico) {
		for (DigitoUnico digitoUnico : listaDigitoUnico) {
			digitoUnicoRepository.delete(digitoUnico);
		}
	}

	public List<DigitoUnico> buscarDigitosCalculadosPorUsuario(Long id) {
		Usuario usuario = usuarioService.buscarUsuarioPorID(id);
		List<DigitoUnico> listaDigitoUnico = digitoUnicoRepository.findByUsuario(usuario);
		if(listaDigitoUnico != null && !listaDigitoUnico.isEmpty())
			return listaDigitoUnico;
		else
			throw new DigitoUnicoException("Não existe(m) digítio(s) calculado(s) para o usuário de código " +id);
	}
}
