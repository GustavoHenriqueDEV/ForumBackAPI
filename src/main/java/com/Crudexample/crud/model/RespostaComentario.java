package com.Crudexample.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resposta_comentario")
public class RespostaComentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idresposta")
    private Long idresposta;

    public RespostaComentario(Long idresposta, Comentario comentario, Usuario usuario, String conteudo) {
        this.idresposta = idresposta;
        this.comentario = comentario;
        this.usuario = usuario;
        this.conteudo = conteudo;
    }
    @ManyToOne
    @JoinColumn(name = "idcomentario", referencedColumnName = "idcomentario", nullable = false)
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    public RespostaComentario() {

    }
    // Getters e Setters
    public Long getIdresposta() {
        return idresposta;
    }

    public void setIdresposta(Long idresposta) {
        this.idresposta = idresposta;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
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
