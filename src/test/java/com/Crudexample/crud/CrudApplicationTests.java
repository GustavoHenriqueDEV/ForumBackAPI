package com.Crudexample.crud;

import com.Crudexample.crud.model.Usuario;
import com.Crudexample.crud.repository.UsuarioRepository;
import com.Crudexample.crud.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CrudApplicationTests {

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioService usuarioService;

	@Test
	public void testaCreateUser(){

		Usuario mockUsuario = new Usuario(1L, "João Silva", "joao123", "senhaSegura", "joao@example.com", 30);
		when(usuarioRepository.save(mockUsuario)).thenReturn(mockUsuario);

		Usuario usuarioResult = usuarioService.create(mockUsuario);

		assertNotNull(usuarioResult);
		assertNotNull(usuarioResult.getIdusuario());
		assertEquals("João Silva", usuarioResult.getNome());
		assertEquals("joao123", usuarioResult.getLogin());
		assertEquals("senhaSegura", usuarioResult.getSenha());
		assertEquals(30, usuarioResult.getIdade());
		assertEquals("joao@example.com", usuarioResult.getEmail());

		verify(usuarioRepository, times(1)).save(usuarioResult);

 	}
	 @Test
	 public void deveriaLancarErroComNomeInvalido(){
		 Usuario mockUsuario = new Usuario(1L, "", "joao123", "senhaSegura", "joao@example.com", 30);

		 IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->{
			 usuarioService.create(mockUsuario);
		 } );
		 assertEquals("O nome de usuario não pode ser invalido.", exception.getMessage());

		 verify(usuarioRepository, never()).save(mockUsuario);
	 }
	@Test
	public void testDeleteUserNotFound() {
		int userId = 1;
		when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			usuarioService.delete(userId);
		});

		assertEquals("Usuário com ID 1 não encontrado.", exception.getMessage());
		verify(usuarioRepository).findById(userId);
		verify(usuarioRepository, never()).delete(any());
	}
	@Test
	public void testDeleteUserSuccess() {
		int userId = 1;
		Usuario usuario = new Usuario(1L, "João Silva", "joao123", "senhaSegura", "joao@example.com", 30);
		when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

		Usuario deletedUsuario = usuarioService.delete(userId);

		assertEquals(usuario, deletedUsuario);
		verify(usuarioRepository).findById(userId);
		verify(usuarioRepository).delete(usuario);
	}
	@Test
	public void deveriaFazerUpdateUser() {
		Usuario existingUsuario = new Usuario(1L, "João Silva", "joao123", "senha123", "joao@example.com", 30);

		Usuario updatedDetails = new Usuario(1L, "João Santos", "joaosantos", "novaSenha", "joaosantos@example.com", 35);

		when(usuarioRepository.findById(1)).thenReturn(Optional.of(existingUsuario));
		when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Usuario result = usuarioService.update(1, updatedDetails);

		verify(usuarioRepository).findById(1);
		verify(usuarioRepository).save(existingUsuario);

		assertEquals("João Santos", result.getNome());
		assertEquals("joaosantos", result.getLogin());
		assertEquals("novaSenha", result.getSenha());
		assertEquals("joaosantos@example.com", result.getEmail());
		assertEquals(35, result.getIdade());
	}
}

