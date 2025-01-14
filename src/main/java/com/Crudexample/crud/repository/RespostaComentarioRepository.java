package com.Crudexample.crud.repository;

import com.Crudexample.crud.model.RespostaComentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaComentarioRepository extends JpaRepository<RespostaComentario, Long> {
    List<RespostaComentario> findByComentarioIdcomentario(Long idcomentario);
}
