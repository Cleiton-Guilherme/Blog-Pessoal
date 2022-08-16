package com.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogpessoal.Repository.UsuarioRepository;
import com.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {

		usuarioRepository.deleteAll();
		usuarioRepository.save(new Usuario(0L, "teste1 silva", "url/foto", "teste@teste.com", "12345678"));
		usuarioRepository.save(new Usuario(0L, "teste2 silva", "url/foto2", "teste@teste2.com", "12345678"));
		usuarioRepository.save(new Usuario(0L, "teste3 silva", "url/foto3", "teste@teste3.com", "12345678"));
		usuarioRepository.save(new Usuario(0L, "teste4", "url/foto4", "teste@teste4.com", "12345678"));

	}
	
	@Test
	@DisplayName("Retorna 1 Usuario")
	public void deveretornarUsuario() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("teste@teste.com");
		assertTrue(usuario.get().getUsuario().equals("teste@teste.com"));
	}
	
	@Test
	@DisplayName("Retornar 3 usuarios")
	public void deveRetornarTresUsuarios() {
		
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("teste1 silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("teste2 silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("teste3 silva"));
	}
}
