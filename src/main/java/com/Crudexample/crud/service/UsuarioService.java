package com.Crudexample.crud.service;

import com.Crudexample.crud.model.Post;
import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.PostRepository;
import com.Crudexample.crud.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PostRepository postRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PostRepository postRepository) {
        this.usuarioRepository = usuarioRepository;
        this.postRepository = postRepository;
    }
    public Optional<Usuario> getUsuarioById(int id){
        return usuarioRepository.findById(id);
    }
    public List<Usuario> buscarPorIdade(int idadeMaior) {
        return usuarioRepository.findByIdadeGreaterThan(idadeMaior);
    }
    public Usuario create(Usuario usuario){
        if(usuario.getNome() == null || usuario.getNome().isEmpty()){
            throw new IllegalArgumentException("O nome de usuario não pode ser invalido.");
        }else{
            return usuarioRepository.save(usuario);
        }
    }
    public Usuario update(int id, Usuario usuarioDetails){
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioDetails.getNome());
            usuario.setLogin(usuarioDetails.getLogin());
            usuario.setSenha(usuarioDetails.getSenha());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setIdade(usuarioDetails.getIdade());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
    }
    public Usuario delete(int id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.delete(usuario);
                    return usuario;
                })
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
    }
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario login(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha)
                .orElse(null);
    }

    public Usuario register(Usuario usuario) {
        if (usuario.getRole() == null) {
            usuario.setRole("USER");
        }
        return usuarioRepository.save(usuario);
    }

    // NOVO MÉTODO: Buscar todos os posts de um usuário
    public List<Post> getAllPostsFromUser(Long idUsuario) {
        // Busca o usuário
        Usuario user = usuarioRepository.findById(Math.toIntExact(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + idUsuario + " não encontrado."));
        // Retorna posts usando "findByUsuarioIdusuario"
        return postRepository.findByUsuarioIdusuario(idUsuario);
    }

    // NOVO MÉTODO: Atualizar apenas nome e senha
    public Usuario updateUserProfile(Long id, String novoNome, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));

        if (novoNome != null && !novoNome.isEmpty()) {
            usuario.setNome(novoNome);
        }
        if (novaSenha != null && !novaSenha.isEmpty()) {
            usuario.setSenha(novaSenha);
        }
        return usuarioRepository.save(usuario);
    }
}

