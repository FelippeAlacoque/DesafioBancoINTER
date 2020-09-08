package com.inter.desafio.DTO;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.inter.desafio.model.DigitoUnico;

public class UsuarioDTO {

	private Long id;
	
	@NotEmpty(message = "Nome é obrigatório.")
	private String nome;
	
	@NotEmpty(message = "E-mail é obrigatório.")
	@Email(message="E-mail inválido.")
	private String email;

    private List<DigitoUnico> digitoUnicoList;
    
    public UsuarioDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<DigitoUnico> getDigitoUnicoList() {
		return digitoUnicoList;
	}

	public void setDigitoUnicoList(List<DigitoUnico> digitoUnicoList) {
		this.digitoUnicoList = digitoUnicoList;
	}
    
    

}
