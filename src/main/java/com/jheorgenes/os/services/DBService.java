package com.jheorgenes.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jheorgenes.os.domain.Cliente;
import com.jheorgenes.os.domain.OS;
import com.jheorgenes.os.domain.Tecnico;
import com.jheorgenes.os.domain.enuns.Prioridade;
import com.jheorgenes.os.domain.enuns.Status;
import com.jheorgenes.os.repositories.ClienteRepository;
import com.jheorgenes.os.repositories.OSRepository;
import com.jheorgenes.os.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private OSRepository osRepository;
	
	public void instanciaDB() {
		
		Tecnico t1 = new Tecnico(null, "Jheorgenes Warlley", "275.188.010-00", "(62) 98888-8888");
		Cliente c1 = new Cliente(null, "Betina Campos", "231.266.630-87", "(62) 98888-7777");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OD", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1); /* Injetando o técnico da Ordem de serviço na lista de técnicos */
		c1.getList().add(os1); /* Injetando o cliente da Ordem de serviço na lista de cliente */

		tecnicoRepository.saveAll(Arrays.asList(t1)); /* Salvando a lista de técnico na tabela tecnico */
		clienteRepository.saveAll(Arrays.asList(c1)); /* Salvando a lista de cliente na tabela cliente */
		osRepository.saveAll(Arrays.asList(os1)); /* Salvando a lista de OS na tabela OS */
	}
}
