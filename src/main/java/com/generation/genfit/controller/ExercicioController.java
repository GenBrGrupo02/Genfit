package com.generation.genfit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.genfit.model.Exercicio;
import com.generation.genfit.repository.ExercicioRepository;
import com.generation.genfit.repository.ObjetivoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/exercicio")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExercicioController {

	@Autowired
	private ObjetivoRepository objetivoRepository;
	
	@Autowired
	private ExercicioRepository exercicioRepository;
	
	
	
	@GetMapping
	public ResponseEntity<List<Exercicio>> getAll(){
		return ResponseEntity.ok(exercicioRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Exercicio> getById(@PathVariable Long id){
		return exercicioRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	

	@PostMapping
	public ResponseEntity<Exercicio> post(@Valid @RequestBody Exercicio exercicio){
		if (objetivoRepository.existsById(exercicio.getObjetivo().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(exercicioRepository.save(exercicio));
			
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Objetivo não existe!", null);
	}
	
	@PutMapping
	public ResponseEntity<Exercicio> put(@Valid @RequestBody Exercicio exercicio){
		if (exercicioRepository.existsById(exercicio.getId())){
			
			if (objetivoRepository.existsById(exercicio.getObjetivo().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(exercicioRepository.save(exercicio));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Objetivo não existe!", null);
			
		}			
			
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Exercicio> postagem = exercicioRepository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		exercicioRepository.deleteById(id);				
	}
	
}
