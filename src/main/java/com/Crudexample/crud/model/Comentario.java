package com.Crudexample.crud.model;

import jakarta.persistence.*;
@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomentario")
    private Long idcomentario;

    @ManyToOne
    @JoinColumn(name = "idpost", referencedColumnName = "idpost", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    public Long getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(Long idcomentario) {
        this.idcomentario = idcomentario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}

