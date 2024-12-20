package com.Crudexample.crud.controller;

import com.Crudexample.crud.model.Comentario;
import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.PostRepository;
import com.Crudexample.crud.service.ComentarioService;
import com.Crudexample.crud.service.LikeService;
import com.Crudexample.crud.service.PostService;
import com.Crudexample.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final LikeService likeService;
    private final PostService postService;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioService comentarioService;


    @Autowired
    private PostRepository postRepository;

    @Autowired
    public PostController(LikeService likeService, PostService postService, UsuarioRepository usuarioRepository, ComentarioService comentarioService ) {
        this.likeService = likeService;
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
            System.out.println(post.toString());

            Usuario usuario = usuarioRepository.findById(post.getUsuario().getIdusuario())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
            post.setUsuario(usuario);

            if (post.getImagem() != null) {
                try {
                    byte[] imagemBytes = post.getImagem();
                    String imagemBase64 = Base64.getEncoder().encodeToString(imagemBytes);

                    System.out.println("Imagem Base64 gerada: " + imagemBase64);

                    post.setImagem(imagemBytes);
                    post.setImagemBase64(imagemBase64);

                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Imagem fornecida não é válida.");
                }
            } else {
                post.setImagem(null);
            }
            // Criar o post
            Post createdPost = postService.createPost(post);

            // Resposta com a string Base64 (opcional, caso o front-end precise)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Post criado com sucesso: " + createdPost.toString());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getReason());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar o post: " + e.getMessage());
        }
    }
    @PostMapping("/{postId}/likes")
    public ResponseEntity<?> darLike(@PathVariable("postId") Long postId, @RequestParam Long idusuario) {
        try {
            likeService.darLike(postId, idusuario);
            // Busca a quantidade atualizada de likes no post
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
            Long idUsuario = Long.valueOf(comentario.getUsuario() != null ? comentario.getUsuario().getIdusuario() : null);
            if (idUsuario == null) {
                throw new RuntimeException("O campo 'usuario' no comentário não pode ser nulo");
            }
            Usuario usuario = usuarioRepository.findById(Math.toIntExact(idUsuario))
                    .orElseThrow(() -> new RuntimeException("Usuário com ID " + idUsuario + " não encontrado"));
            comentario.setUsuario(usuario);
            comentarioService.adicionarComentario(id, comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comentário adicionado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar comentário: " + e.getMessage());
        }
    }
}
