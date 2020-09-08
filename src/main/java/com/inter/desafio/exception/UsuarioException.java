package com.inter.desafio.exception;

public class UsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioException(String errorMessage) {
        super(errorMessage);
    }

}
