package com.Crudexample.crud.controller;

import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.service.PostService;
import com.Crudexample.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PostController(PostService postService, UsuarioRepository usuarioRepository) {
        this.postService = postService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }


    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        try {
            Usuario usuario = usuarioRepository.findById(post.getUsuario().getIdusuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            post.setUsuario(usuario); // Associar o usuário ao post

            Post createdPost = postService.createPost(post); // Criar o post
            return ResponseEntity.status(HttpStatus.CREATED).body("Post criado com sucesso: " + createdPost.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o post: " + e.getMessage());
        }
    }
}
