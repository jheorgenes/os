package com.jheorgenes.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jheorgenes.os.domain.Pessoa;
import com.jheorgenes.os.domain.Tecnico;
import com.jheorgenes.os.dtos.TecnicoDTO;

import com.jheorgenes.os.repositories.PessoaRepository;
import com.jheorgenes.os.repositories.TecnicoRepository;
import com.jheorgenes.os.services.exceptions.DataIntegratyViolationException;
import com.jheorgenes.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
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
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		
		/* Verifica se o novo CPF existe e se ele é diferente do CPF do antigo objeto  */
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		/* Atualizando os campos */
		oldObj.setNome(objDTO.getNome()); 
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return tecnicoRepository.save(oldObj); /* Já salva no banco de dados e já retorna o objeto salvo */
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() > 0) { /* Se houver alguma item na lista de Ordem de serviço relacionado ao Técnico */
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
	}

	/* Método para validar se o CPF do argumento é igual a algum cpf do banco de dados */
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());

		if(obj != null) { //Se houver um objeto com o cpf cadastrado, retorna o objeto
			return obj;
		}
		return null; //Senão retorna null
	}
}
