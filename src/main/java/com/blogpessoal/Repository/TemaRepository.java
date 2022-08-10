package com.blogpessoal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blogpessoal.model.tema;
 
@Repository
public interface TemaRepository extends JpaRepository<tema, Long>{
	public List<tema>findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
	


}