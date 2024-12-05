package com.Crudexample.crud.service;

import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
}

