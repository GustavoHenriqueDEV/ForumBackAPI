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
        // Buscar o post pelo ID
        Post post = postRepository.findById(Math.toIntExact(idpost))
                .orElseThrow(() -> new RuntimeException("Post com ID " + idpost + " não encontrado"));

        // Verificar se o conteúdo do comentário é válido
        if (comentario.getConteudo() == null || comentario.getConteudo().trim().isEmpty()) {
            throw new IllegalArgumentException("O comentário não pode ser vazio");
        }

        // Associar o post ao comentário
        comentario.setPost(post);

        // Salvar o comentário no repositório
        return comentarioRepository.save(comentario);
    }



    public List<Comentario> getComentariosByPost(Long idpost) {
        // Buscar o post, caso não exista lançar uma exceção
        Post post = postRepository.findById(Math.toIntExact(idpost))
                .orElseThrow(() -> new RuntimeException("Post com ID " + idpost + " não encontrado"));

        // Chamar o repositório para pegar os comentários com o nome do autor
        return comentarioRepository.findComentariosByPostWithAutor(idpost);
    }


}
