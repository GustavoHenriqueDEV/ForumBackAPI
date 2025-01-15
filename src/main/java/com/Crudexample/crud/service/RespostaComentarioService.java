package com.Crudexample.crud.service;

import com.Crudexample.crud.model.Comentario;
import com.Crudexample.crud.model.RespostaComentario;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.ComentarioRepository;
import com.Crudexample.crud.repository.RespostaComentarioRepository;
import com.Crudexample.crud.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespostaComentarioService {

    private final RespostaComentarioRepository respostaComentarioRepository;
    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;

    public RespostaComentarioService(RespostaComentarioRepository respostaComentarioRepository,
                                     ComentarioRepository comentarioRepository,
                                     UsuarioRepository usuarioRepository) {
        this.respostaComentarioRepository = respostaComentarioRepository;
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RespostaComentario adicionarResposta(Long idcomentario, Long idusuario, String conteudo) {
        Comentario comentario = comentarioRepository.findById(idcomentario)
                .orElseThrow(() -> new RuntimeException("Comentário com ID " + idcomentario + " não encontrado"));

        Usuario usuario = usuarioRepository.findById(Math.toIntExact(idusuario))
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + idusuario + " não encontrado"));

        RespostaComentario resposta = new RespostaComentario();
        resposta.setComentario(comentario);
        resposta.setUsuario(usuario);
        resposta.setConteudo(conteudo);

        return respostaComentarioRepository.save(resposta);
    }

    public List<RespostaComentario> listarRespostasPorComentario(Long idcomentario) {
        return respostaComentarioRepository.findByComentarioIdcomentario(idcomentario);
    }
}
