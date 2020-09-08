package com.inter.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.desafio.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findById(long id);
}
