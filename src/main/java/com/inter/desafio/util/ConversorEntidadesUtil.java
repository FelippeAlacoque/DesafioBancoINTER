package com.inter.desafio.util;

import com.inter.desafio.DTO.DigitoUnicoDTO;
import com.inter.desafio.DTO.UsuarioDTO;
import com.inter.desafio.model.DigitoUnico;
import com.inter.desafio.model.Usuario;

public class ConversorEntidadesUtil {
	
	/**
	 * Converte Usuario em UsuarioDTO
	 * @param usuario
	 * @return
	 */
	public static UsuarioDTO convertUsuarioForUsuarioDTO(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(usuario.getId());
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setDigitoUnicoList(usuario.getDigitoUnicoList());
		
		return usuarioDTO;
	}
	
	/**
	 * Converte UsuarioDTO para Usuario
	 * @param usuarioDto
	 * @return
	 */
	public static Usuario convertUsuarioDtoForUsuario(UsuarioDTO usuarioDto){
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDto.getId());
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        return usuario;
    }

	/**
	 * Converte DigitoUnico em DigitoUnicoDTO
	 * @param digitoUnico
	 * @return
	 */
	public static DigitoUnicoDTO convertDigitoUnicoForDigitoUnicoDto(DigitoUnico digitoUnico){
	    DigitoUnicoDTO digitoUnicoDto = new DigitoUnicoDTO();
	    digitoUnicoDto.setId(digitoUnico.getId());
	    digitoUnicoDto.setNumeroRepeticao(digitoUnico.getNumeroRepeticao());
	    digitoUnicoDto.setPalavra(digitoUnico.getPalavra());
	    digitoUnicoDto.setResultado(digitoUnico.getResultado());
	    digitoUnicoDto.setUsuario(digitoUnico.getUsuario());
	    
	    return digitoUnicoDto;
	}

}
