package com.generation.genfit.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_objetivos")
public class Objetivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_plano;

	@NotNull(message = "O atributo descrição é obrigatório")
	private String descricao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "objetivo", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("objetivo")
	private List<Exercicio> exercicio;

	public Long getId_plano() {
		return id_plano;
	}

	public void setId_plano(Long id_plano) {
		this.id_plano = id_plano;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Exercicio> getExercicio() {
		return exercicio;
	}

	public void setExercicio(List<Exercicio> exercicio) {
		this.exercicio = exercicio;
	}

}