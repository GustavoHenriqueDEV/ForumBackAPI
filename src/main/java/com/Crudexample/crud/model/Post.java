package com.Crudexample.crud.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "data_criacao", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCriacao;

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

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

    public String getImagembase64() {
        return imagembase64;
    }
    public void setImagembase64(String imagembase64) {
        this.imagembase64 = imagembase64;
    }
    @Column(name = "imagembase64", columnDefinition="LONGTEXT" )
    private String imagembase64;

    @Column(name = "imagem", columnDefinition = "LONGBLOB")
    private byte[] imagem;

    public byte[] getImagem() {
        return imagem;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    public Post() {}

    public Post(Long idpost, String titulo, String tipo, Usuario usuario, String conteudo, int likes, byte[] imagem, LocalDateTime dataCriacao) {
        this.idpost = idpost;
        this.titulo = titulo;
        this.tipo = tipo;
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.likes = likes;
        this.imagem = imagem;
        this.dataCriacao = dataCriacao;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

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

    @Override
    public String toString() {
        return "Post{" +
                "idpost=" + idpost +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", usuario=" + usuario +
                ", conteudo='" + conteudo + '\'' +
                ", likes=" + likes +
                ", imagem=" + Arrays.toString(imagem) +
                ", comentarios=" + comentarios +
                '}';
    }
}
