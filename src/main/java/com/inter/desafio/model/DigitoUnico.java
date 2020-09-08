package com.inter.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class DigitoUnico {
	
	@Id
	@Column(name = "id_dig_unico")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Integer numeroRepeticao;
	
	@NotNull
	private String palavra;
	
	private Integer resultado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idusuario")
	@JsonBackReference
	private Usuario usuario;

	public DigitoUnico() {
		super();
	}

	public DigitoUnico(@NotNull Integer numeroRepeticao, @NotNull String palavra) {
		super();
		this.numeroRepeticao = numeroRepeticao;
		this.palavra = palavra;
	}

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getResultado() {
		return resultado;
	}

	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DigitoUnico other = (DigitoUnico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
