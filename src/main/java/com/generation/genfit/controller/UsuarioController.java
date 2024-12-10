package com.generation.genfit.controller;

import com.generation.genfit.model.Usuario;
import com.generation.genfit.model.UsuarioLogin;
import com.generation.genfit.repository.UsuarioRepository;
import com.generation.genfit.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return usuarioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin) {
        return usuarioService.autenticarUsuario(usuarioLogin).map(resposta -> ResponseEntity.status(HttpStatus.OK)
                .body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> postUsuario(@RequestBody @Valid Usuario usuario) {
        return usuarioService.cadastrarUsuario(usuario).map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                .body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(usuario).map(resposta -> ResponseEntity.status(HttpStatus.OK)
                .body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/{id}/calcular")
    public ResponseEntity<String> calcularImc(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id).orElse(null);
            usuarioService.calcularImc(id, usuario.getAltura(), usuario.getPeso());
            return ResponseEntity.ok("IMC calculado e salvo com sucesso!");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }



}