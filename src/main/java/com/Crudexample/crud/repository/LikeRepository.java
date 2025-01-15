package com.Crudexample.crud.repository;

import com.Crudexample.crud.model.Like;
import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {


    @Query("SELECT l FROM Like l WHERE l.post = :post AND l.usuario = :usuario")
    Optional<Like> findByPostAndUsuario(@Param("post") Post post, @Param("usuario") Usuario usuario);

    @Query("SELECT l FROM Like l WHERE l.post = :post")
    List<Like> findLikesByPost(@Param("post") Post post);
}