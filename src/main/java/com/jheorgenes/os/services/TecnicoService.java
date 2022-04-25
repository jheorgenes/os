package com.jheorgenes.os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jheorgenes.os.domain.Tecnico;
import com.jheorgenes.os.dtos.TecnicoDTO;
import com.jheorgenes.os.repositories.TecnicoRepository;
import com.jheorgenes.os.services.exceptions.DataIntegratyViolationException;
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

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		if(findByCPF(objDTO) != null) { /* Verifica se o cpf já existe */
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return tecnicoRepository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone())); /*Se não existir, salva o registro no banco e retorna o objeto Tecnico */
	}
	
	/* Método para validar se o CPF do argumento é igual a algum cpf do banco de dados */
	private Tecnico findByCPF(TecnicoDTO objDTO) {
		Tecnico obj = tecnicoRepository.findByCPF(objDTO.getCpf());
		if(obj != null) { //Se houver um objeto com o cpf cadastrado, retorna o objeto
			return obj;
		}
		return null; //Senão retorna null
	}
}
