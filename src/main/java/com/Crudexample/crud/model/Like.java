package com.Crudexample.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlike")
    private Long idlike;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idpost", referencedColumnName = "idpost", nullable = false)
    private Post post;

    public Like() {}

    public Like(Usuario usuario, Post post) {
        this.usuario = usuario;
        this.post = post;
    }

    public Long getIdlike() {
        return idlike;
    }

    public void setIdlike(Long idlike) {
        this.idlike = idlike;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

