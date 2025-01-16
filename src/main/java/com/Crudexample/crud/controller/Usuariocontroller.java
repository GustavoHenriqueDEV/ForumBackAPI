package com.Crudexample.crud.controller;

import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.UsuarioRepository;
import com.Crudexample.crud.service.JwtService;
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

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
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


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario user = usuarioService.login(usuario.getLogin(), usuario.getSenha());
        if (user != null) {
            String token = jwtService.generateToken(
                    (long) user.getIdusuario(),
                    user.getNome(),
                    user.getRole() // <-- passe a role também
            );

            Map<String, Object> response = new HashMap<>();
            response.put("idusuario", user.getIdusuario());
            response.put("nome", user.getNome());
            response.put("role", user.getRole()); // Se quiser retornar
            response.put("token", token);

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
    @GetMapping("/{id}/posts")
    public ResponseEntity<?> getPostsFromUser(@PathVariable Long id) {
        try {
            List<Post> posts = usuarioService.getAllPostsFromUser(id);
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }
    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        try {
            String novoNome = body.get("nome");
            String novaSenha = body.get("senha");
            Usuario usuarioAtualizado = usuarioService.updateUserProfile((long) Math.toIntExact(id), novoNome, novaSenha);
            return ResponseEntity.ok("Usuário atualizado: " + usuarioAtualizado.toString());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar perfil: " + e.getMessage());
        }
    }

}

