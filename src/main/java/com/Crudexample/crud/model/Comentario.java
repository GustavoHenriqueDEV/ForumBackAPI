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

    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @Column(name = "autor")
    private String autor; // Opcional: para registrar o autor do comentário

    public Comentario() {}

    public Comentario(Long idcomentario, Post post, String conteudo, String autor) {
        this.idcomentario = idcomentario;
        this.post = post;
        this.conteudo = conteudo;
        this.autor = autor;
    }

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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
