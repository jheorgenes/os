package com.jheorgenes.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jheorgenes.os.domain.Tecnico;
import com.jheorgenes.os.dtos.TecnicoDTO;
import com.jheorgenes.os.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO objDTO = new TecnicoDTO(tecnicoService.findById(id)); //Instanciando um TecnicoDTO (passando como argumento o objeto tecnico que será retornado do tecnicoService)
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		/* Percorre uma lista de TecnicoService (obtido do banco de dados) fazendo a busca: para cada novo objetoDTO um objeto tecnico transformado */
		List<TecnicoDTO> listDTO = tecnicoService.findAll().stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
		
//		List<Tecnico> list = tecnicoService.findAll();
//		List<TecnicoDTO> listDTO = new ArrayList<>();
//		
//		/* Alterando uma lista da forma antiga */
////		for(Tecnico obj : list) {
////			listDTO.add(new TecnicoDTO(obj));
////		}
//		
//		/* Alterando uma lista a partir do Java 8 */
//		list.forEach(obj -> listDTO.add(new TecnicoDTO(obj)));	
	}
	
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){
		Tecnico newObj = tecnicoService.create(objDTO); //Buscando no banco de dados
		URI uri = ServletUriComponentsBuilder /* Chamando a classe para montar a URL completa */
					.fromCurrentRequest() /* buscando o caminho completo da requisição */
					.path("/{id}") /* Definindo que "{id}" é uma variável */
					.buildAndExpand(newObj.getId()) /* Substituíndo a variável pelo id do objeto */
					.toUri(); /* montando a URL completa com as informações passadas */
		return ResponseEntity.created(uri).build(); /* Retornando um responseEntity do tipo created (com a uri completa como argumento) e compilando o processo da requisição */
	}
	
	
}
