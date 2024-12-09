package com.Crudexample.crud.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    // Construtor sem argumentos (necessário para o Hibernate)
    public Post() {}

    // Construtor com parâmetros
    public Post(Long idpost, String titulo, String tipo, Usuario usuario, String conteudo, int likes) {
        this.idpost = idpost;
        this.titulo = titulo;
        this.tipo = tipo;
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.likes = likes;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

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

}
