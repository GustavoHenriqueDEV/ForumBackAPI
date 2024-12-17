package com.Crudexample.crud.controller;

import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.UsuarioRepository;
import com.Crudexample.crud.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class Usuariocontroller {

    private final UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll(); // Método no service para listar todos os usuários
        return ResponseEntity.ok(usuarios);
    }

    public Usuariocontroller(UsuarioService usuarioService, UsuarioRepository usuarioRepository, UsuarioRepository usuarioRepository1) {
        this.usuarioService = usuarioService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getUsuarioById(@PathVariable int id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario.toString()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontraod"));
    }

    @PostMapping
    public ResponseEntity<String> createUsuario(@RequestBody Usuario usuario) {

        try {
            usuarioService.create(usuario);
            return ResponseEntity.ok(usuario.toString());
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não foi criado" + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.update(id, usuario);
        return ResponseEntity.ok("O Usuario " + usuarioAtualizado.toString() + " foi atualizado" );
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O usuario não foi encontrado" + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable int id) {
        try {
            Usuario usuarioDeletado = usuarioService.delete(id);
            return ResponseEntity.ok("O usuario foi deletado com sucesso " + usuarioDeletado.toString());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao tentar deletar o usuário: " + e.getMessage());
        }
    }


    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario user = usuarioService.login(usuario.getLogin(), usuario.getSenha());
        if (user != null) {
            // Retorna o ID e o nome do usuário
            Map<String, Object> response = new HashMap<>();
            response.put("idusuario", user.getIdusuario());
            response.put("nome", user.getNome());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }


    // Endpoint para registrar
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        try {
            Usuario newUser = usuarioService.register(usuario);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

