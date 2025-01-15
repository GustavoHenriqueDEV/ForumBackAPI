package com.Crudexample.crud.controller;

import com.Crudexample.crud.model.Comentario;
import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.RespostaComentario;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.PostRepository;
import com.Crudexample.crud.service.ComentarioService;
import com.Crudexample.crud.service.LikeService;
import com.Crudexample.crud.service.PostService;
import com.Crudexample.crud.repository.UsuarioRepository;
import com.Crudexample.crud.service.RespostaComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final LikeService likeService;
    private final PostService postService;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioService comentarioService;
    private final RespostaComentarioService respostaComentarioService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    public PostController(LikeService likeService, PostService postService, UsuarioRepository usuarioRepository,
                          ComentarioService comentarioService, RespostaComentarioService respostaComentarioService) {
        this.likeService = likeService;
        this.postService = postService;
        this.usuarioRepository = usuarioRepository;
        this.comentarioService = comentarioService;
        this.respostaComentarioService = respostaComentarioService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPosts() {
        List<Map<String, Object>> posts = postService.findAllPostsWithUserNames();
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        try {
            Usuario usuario = usuarioRepository.findById(post.getUsuario().getIdusuario())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
            post.setUsuario(usuario);

            // Configura a data de criação
            post.setDataCriacao(LocalDateTime.now());
            if (post.getImagem() != null) {
                byte[] imagemBytes = post.getImagem();
                String imagemBase64 = Base64.getEncoder().encodeToString(imagemBytes);
                post.setImagem(imagemBytes);
                post.setImagembase64(imagemBase64);
            }
            Post createdPost = postService.createPost(post);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Post criado com sucesso: " + createdPost.toString());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar o post: " + e.getMessage());
        }
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<?> darLike(@PathVariable("postId") Long postId, @RequestParam Long idusuario) {
        try {
            likeService.darLike(postId, idusuario);
            int updatedLikes = postRepository.findById(Math.toIntExact(postId))
                    .map(Post::getLikes)
                    .orElseThrow(() -> new RuntimeException("Post não encontrado"));
            return ResponseEntity.ok(updatedLikes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar/remover like: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<String> adicionarComentario(@PathVariable Long id, @RequestBody Comentario comentario) {
        try {
            Long idUsuario = Long.valueOf(Optional.ofNullable(comentario.getUsuario())
                    .map(Usuario::getIdusuario)
                    .orElseThrow(() -> new RuntimeException("O campo 'usuario' no comentário não pode ser nulo")));
            Usuario usuario = usuarioRepository.findById(Math.toIntExact(idUsuario))
                    .orElseThrow(() -> new RuntimeException("Usuário com ID " + idUsuario + " não encontrado"));
            comentario.setUsuario(usuario);
            comentarioService.adicionarComentario(id, comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comentário adicionado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar comentário: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<Comentario>> getComentariosByPost(@PathVariable Long id) {
        try {
            List<Comentario> comentarios = comentarioService.getComentariosByPost(id);
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        // se chegou aqui, é ADMIN
        postRepository.deleteById(Math.toIntExact(id));
        return ResponseEntity.ok("Deletado com sucesso");
    }


    @GetMapping("/{postId}")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable Long postId) {
        try {
            Optional<Post> post = postService.findPostByID(postId.intValue());
            return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
