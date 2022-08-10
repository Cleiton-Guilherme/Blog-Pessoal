package com.blogpessoal.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.blogpessoal.Repository.TemaRepository;
import com.blogpessoal.model.tema;

@RestController
@RequestMapping("/Tema")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public abstract class TemaController {
	
	private TemaRepository temaRepository;
	
	public ResponseEntity<List<tema>>getAll(){
		return ResponseEntity.ok(temaRepository.findAll());
	} 
		
		//SELECT * FROM tb_postagens;
		
	@GetMapping("/{id}")
	public ResponseEntity<tema> getById(@PathVariable Long id) {
		return temaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}

		
		    @GetMapping("/descrição/{descrição}")	
		    public ResponseEntity<List<tema>>	getByDescricao(@PathVariable String descricao){
			   return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
		    }
			
			@PostMapping
			public ResponseEntity<tema> post(@Validated @RequestBody tema tema){
				return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
			}
		
			
			@PutMapping
			public ResponseEntity<tema> putTema(@Valid @RequestBody tema tema) {
					
				return temaRepository.findById(tema.getId())
						.map(resposta -> {
							return ResponseEntity.ok().body(temaRepository.save(tema));
						})
						.orElse(ResponseEntity.notFound().build());
			}

	
			@DeleteMapping("/{id}")
			public void delete(@PathVariable Long id) {
			     Optional<tema> postagem = temaRepository.findById(id);
					
				if(postagem.isEmpty())
					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
					
				temaRepository.deleteById(id);
				
				
				}
						
  }
	

