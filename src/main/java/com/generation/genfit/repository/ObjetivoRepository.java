package com.generation.genfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.genfit.model.Objetivo;

@Repository
public interface ObjetivoRepository extends JpaRepository<Objetivo, Long>{

	public List<Objetivo> findAllByDescricaoContainingIgnoreCase(@Param("descricao")String descricao);
	
}
