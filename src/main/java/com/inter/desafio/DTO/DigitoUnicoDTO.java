package com.inter.desafio.DTO;

import javax.validation.constraints.NotNull;

import com.inter.desafio.model.Usuario;

public class DigitoUnicoDTO {

	@NotNull
	private Long id;
	@NotNull
	private Integer numeroRepeticao;
	@NotNull
	private String palavra;
	
	private Integer resultado;
	
	private Usuario usuario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumeroRepeticao() {
		return numeroRepeticao;
	}
	public void setNumeroRepeticao(Integer numeroRepeticao) {
		this.numeroRepeticao = numeroRepeticao;
	}
	public String getPalavra() {
		return palavra;
	}
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	public Integer getResultado() {
		return resultado;
	}
	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public DigitoUnicoDTO(@NotNull Integer numeroRepeticao, @NotNull String palavra, Integer resultado,
			Usuario usuario) {
		super();
		this.numeroRepeticao = numeroRepeticao;
		this.palavra = palavra;
		this.resultado = resultado;
		this.usuario = usuario;
	}
	
	public DigitoUnicoDTO() {
		
	}
	
}
