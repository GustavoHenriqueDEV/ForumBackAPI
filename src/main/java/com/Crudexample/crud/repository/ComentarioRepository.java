package com.Crudexample.crud.repository;

import com.Crudexample.crud.model.Comentario;
import com.Crudexample.crud.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPost(Post post);
}
