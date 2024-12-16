package com.Crudexample.crud.service;

import com.Crudexample.crud.model.Like;
import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.LikeRepository;
import com.Crudexample.crud.repository.PostRepository;
import com.Crudexample.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void darLike(Long postId, Long usuarioId) {
        // Verifica se o post existe
        Post post = postRepository.findById(Math.toIntExact(postId))
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(Math.toIntExact(usuarioId))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o like já existe
        Optional<Like> likeExistente = likeRepository.findByPostAndUsuario(post, usuario);
        if (likeExistente.isPresent()) {
            // Se o like já existe, remove o like
            likeRepository.delete(likeExistente.get());
            post.setLikes(post.getLikes() - 1); // Decrementa o contador de likes
        } else {
            // Caso contrário, adiciona o like
            Like novoLike = new Like();
            novoLike.setPost(post);
            novoLike.setUsuario(usuario);
            likeRepository.save(novoLike);
            post.setLikes(post.getLikes() + 1); // Incrementa o contador de likes
        }

        // Atualiza o post
        postRepository.save(post);
    }

}
