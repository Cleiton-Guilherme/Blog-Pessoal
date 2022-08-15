package com.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blogpessoal.Repository.UsuarioRepository;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		
		usuarioRepository.deleteAll();
	}
	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuário() {
		
		HttpEntity<Usuario> resposta = new HttpEntity<Usuario>(new Usuario(0L,
				"teste4", "url/foto04", "teste@teste4.com", "12345678"));
		
		ResponseEntity<Usuario> resposta1 = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, resposta, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getBody());
		assertEquals(resposta.getBody(). getNome(), resposta.getBody().getNome());
		assertEquals(resposta.getBody(). getFoto(), resposta.getBody().getFoto());
		assertEquals(resposta.getBody(). getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L,"teste5", "Url/foto5", "teste@teste5.com", "12345678"));
	
	
	HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "teste5", "Url/foto5", "teste@teste5.com", "12345678"));
	
	ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/Usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
	
	assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, "teste6", "Url/foto6", "teste@teste6.com", "12345678"));
		
		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(),"teste6", "Url/foto6", "teste@teste6.com", "12345678");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getFoto(), resposta.getBody().getFoto());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
		
		
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostarTodosUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "teste7", "Url/foto7", "teste@teste7.com", "teste12345678"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "teste8", "Url/foto8", "teste@teste8.com", "teste12345678"));
		
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuario/all", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	
}
