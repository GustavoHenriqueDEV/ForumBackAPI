package com.Crudexample.crud.service;

import com.Crudexample.crud.model.Comentario;
import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.ComentarioRepository;
import com.Crudexample.crud.repository.PostRepository;
import com.Crudexample.crud.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PostRepository postRepository;
    private UsuarioRepository usuarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository, PostRepository postRepository) {
        this.comentarioRepository = comentarioRepository;
        this.postRepository = postRepository;
    }

    public Comentario adicionarComentario(Long idpost, Comentario comentario) {
        Post post = postRepository.findById(Math.toIntExact(idpost))
                .orElseThrow(() -> new RuntimeException("Post com ID " + idpost + " não encontrado"));

        if (comentario.getConteudo() == null || comentario.getConteudo().trim().isEmpty()) {
            throw new IllegalArgumentException("O comentário não pode ser vazio");
        }
        comentario.setPost(post);
        return comentarioRepository.save(comentario);
    }
    public List<Comentario> getComentariosByPost(Long idpost) {
        Post post = postRepository.findById(Math.toIntExact(idpost))
                .orElseThrow(() -> new RuntimeException("Post com ID " + idpost + " não encontrado"));
        return comentarioRepository.findComentariosByPostWithAutor(idpost);
    }


}
