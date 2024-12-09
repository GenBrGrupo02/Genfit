package com.generation.genfit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_exercicios")
public class Exercicio {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 	@NotBlank
	 	@Size(min = 2, max = 100, message = "O atributo nome deve conter no mínimo 02 e no máximo 100 caracteres")
	    private String nome;
	 	
	 	@NotBlank
	 	@Size(min = 10, max = 1000, message = "O atributo descrição deve conter no mínimo 05 e no máximo 100 caracteres")
	    private String descricao;

	 	@NotBlank
	    private String dificuldade;

	 	
	    private String videoTutorial;

	    @ManyToOne
	    @JsonIgnoreProperties("exercicio")
	    private Objetivo objetivo; 

	    @ManyToOne
	    @JsonIgnoreProperties("exercicio")
	    private Usuario usuario;

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

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public String getDificuldade() {
			return dificuldade;
		}

		public void setDificuldade(String dificuldade) {
			this.dificuldade = dificuldade;
		}

		public String getVideoTutorial() {
			return videoTutorial;
		}

		public void setVideoTutorial(String videoTutorial) {
			this.videoTutorial = videoTutorial;
		}

		public Objetivo getObjetivo() {
			return objetivo;
		}

		public void setObjetivo(Objetivo objetivo) {
			this.objetivo = objetivo;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		} 
}
