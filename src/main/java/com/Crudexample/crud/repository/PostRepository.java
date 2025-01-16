package com.Crudexample.crud.repository;

import com.Crudexample.crud.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.id = :id")
    void incrementLikes(Long id);

    @Query("SELECT p.likes FROM Post p WHERE p.id = :id")
    Integer findLikesById(Long id);

    List<Post> findByUsuarioIdusuario(Long idusuario);


}
