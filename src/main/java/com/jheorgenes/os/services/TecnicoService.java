package com.jheorgenes.os.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jheorgenes.os.domain.Tecnico;
import com.jheorgenes.os.repositories.TecnicoRepository;
import com.jheorgenes.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id); /* Chamando a persistência pra buscar no banco e retornando um Optional */
		/* Retorna o Objeto OU uma exception (retornando a exeption personalizada) */
		return obj.orElseThrow(
				/* Função anônima para retornar uma exception personalizada */
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()) //Retorna a mensagem descrevendo o ID e o Tipo de classe onde a exception foi encontrada
		);
	}
}
