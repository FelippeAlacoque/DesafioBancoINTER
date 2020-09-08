package com.inter.desafio.model;

public class WrapRequestBody {

	private Long idUsuario;
	private Long idDigUnico;
	private Integer numeroRepeticao;
	private String palavra;	
	private Integer resultado;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}	
	public Long getIdDigUnico() {
		return idDigUnico;
	}
	public void setIdDigUnico(Long idDigUnico) {
		this.idDigUnico = idDigUnico;
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
}
