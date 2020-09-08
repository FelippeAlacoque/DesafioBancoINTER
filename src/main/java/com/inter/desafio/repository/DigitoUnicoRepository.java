package com.inter.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.inter.desafio.model.DigitoUnico;
import com.inter.desafio.model.Usuario;

public interface DigitoUnicoRepository extends JpaRepository<DigitoUnico, Long>, QueryByExampleExecutor<DigitoUnico> {
	List<DigitoUnico> findByUsuario(Usuario usuario);
	void deleteByUsuario(Usuario usuario);
}
