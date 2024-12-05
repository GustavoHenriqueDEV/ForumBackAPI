package com.Crudexample.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpost")
    private Long idpost;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "idusuario", nullable = false)
    private Usuario usuario; // Relacionamento com Usuario

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "likes")
    private int likes;

    @Column(name = "comentario")
    private String comentario;

    // Construtor sem argumentos (necessário para o Hibernate)
    public Post() {}

    // Construtor com parâmetros
    public Post(Long idpost, String titulo, String tipo, Usuario usuario, String conteudo, int likes, String comentario) {
        this.idpost = idpost;
        this.titulo = titulo;
        this.tipo = tipo;
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.likes = likes;
        this.comentario = comentario;
    }

    // Getters e Setters
    public Long getIdpost() {
        return idpost;
    }

    public void setIdpost(Long idpost) {
        this.idpost = idpost;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
