package com.Crudexample.crud.service;

import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

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

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
}
