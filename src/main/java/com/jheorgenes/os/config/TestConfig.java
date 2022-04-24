package com.jheorgenes.os.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jheorgenes.os.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean /* Sempre que a classe TestConfig for chamada, o método abaixo "instanciaDB" será executado */
	public void instanciaDB() {
		this.dbService.instanciaDB();
	}
}
