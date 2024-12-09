package com.generation.genfit.service;

import com.generation.genfit.model.Usuario;
import com.generation.genfit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) return Optional.empty();
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        return Optional.of(usuarioRepository.save(usuario));
    }

    public Optional<Usuario> atualizarUsuario(Usuario usuario) {
        if (usuarioRepository.findById(usuario.getUsuarioId()).isPresent()) {
            Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
            if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getUsuarioId() != usuario.getUsuarioId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
            usuario.setSenha(criptografarSenha(usuario.getSenha()));
            return Optional.ofNullable(usuarioRepository.save(usuario));
        }
        return Optional.empty();
    }


    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
        var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());
        Authentication authentication = authenticationManager.authenticate(credenciais);
        if (authentication.isAuthenticated()) {
            Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
            if (usuario.isPresent()) {
                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
                usuarioLogin.get().setSenha("");

                return usuarioLogin;
            }
        }
        return Optional.empty();
    }

    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);

    }

    private String gerarToken(String usuario) {
        return "Bearer " + jwtService.generateToken(usuario);
    }


    public void calcularImc(Long usuarioId, BigDecimal altura, BigDecimal peso) {
        if (altura == null || peso == null || altura.compareTo(BigDecimal.ZERO) <= 0 || peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Altura e peso devem ser valores positivos.");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        BigDecimal alturaAoQuadrado = altura.pow(2);
        BigDecimal imc = peso.divide(alturaAoQuadrado, 2, RoundingMode.HALF_UP);
        usuario.setImc(imc);
        usuarioRepository.save(usuario);
        System.out.println("IMC do usuário atualizado: " + imc);
    }


}
