package com.Crudexample.crud.controller;

import com.Crudexample.crud.model.Comentario;
import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.PostRepository;
import com.Crudexample.crud.service.ComentarioService;
import com.Crudexample.crud.service.PostService;
import com.Crudexample.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioService comentarioService;


    @Autowired
    private PostRepository postRepository;

    @Autowired
    public PostController(PostService postService, UsuarioRepository usuarioRepository, ComentarioService comentarioService ) {
        this.postService = postService;
        this.usuarioRepository = usuarioRepository;
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPosts() {
        List<Map<String, Object>> posts = postService.findAllPostsWithUserNames();
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        try {
            // Verifica se o usuário associado ao post existe
            Usuario usuario = usuarioRepository.findById(post.getUsuario().getIdusuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            // Associa o usuário encontrado ao post
            post.setUsuario(usuario);
            // Cria o post
            Post createdPost = postService.createPost(post);

            // Retorna a resposta de sucesso com informações do post criado
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Post criado com sucesso: " + createdPost.toString());
        } catch (Exception e) {
            // Retorna erro interno com a mensagem detalhada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar o post: " + e.getMessage());
        }
    }
    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<Comentario>> getComentariosByPost(@PathVariable Long id) {
        try {
            List<Comentario> comentarios = comentarioService.getComentariosByPost(id);
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<String> adicionarComentario(@PathVariable Long id, @RequestBody Comentario comentario) {
        try {
            // Buscar o usuário no banco de dados pelo ID recebido
            Long idUsuario = Long.valueOf(comentario.getUsuario() != null ? comentario.getUsuario().getIdusuario() : null);
            if (idUsuario == null) {
                throw new RuntimeException("O campo 'usuario' no comentário não pode ser nulo");
            }

            Usuario usuario = usuarioRepository.findById(Math.toIntExact(idUsuario))
                    .orElseThrow(() -> new RuntimeException("Usuário com ID " + idUsuario + " não encontrado"));

            // Associar o usuário ao comentário
            comentario.setUsuario(usuario);

            // Chamar o serviço para adicionar o comentário
            comentarioService.adicionarComentario(id, comentario);

            return ResponseEntity.status(HttpStatus.CREATED).body("Comentário adicionado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar comentário: " + e.getMessage());
        }
    }


}
