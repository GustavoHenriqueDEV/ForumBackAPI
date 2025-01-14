package com.Crudexample.crud.service;

import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostService {

    public Optional<Post> findPostByID(int id) {

        return Optional.ofNullable(postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post com o ID " + id + " não foi encontrado.")));
    }


    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post deletePost(int idpost) {
        return postRepository.findById(idpost)
                .map(post -> {
                    postRepository.delete(post);
                    return post;
                }).orElseThrow(() -> new RuntimeException("Post com o id especificado não foi encontrado"));
    }

        @Autowired
        private JdbcTemplate jdbcTemplate;

        public List<Map<String, Object>> findAllPostsWithUserNames() {
            String query = "SELECT post.idpost, post.titulo, post.tipo, post.conteudo, post.likes,  post.iduser, usuario.nome, post.imagem, post.imagembase64, post.data_criacao " +
                    "FROM post " +
                    "INNER JOIN usuario ON post.iduser = usuario.idusuario";
            return jdbcTemplate.queryForList(query);
        }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }


}
