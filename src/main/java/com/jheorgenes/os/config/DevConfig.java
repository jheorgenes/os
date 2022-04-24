package com.jheorgenes.os.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jheorgenes.os.services.DBService;

@Configuration
@Profile("development")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}") /* Pegando o valor do application.properties referente ao tipo de execução no banco */
	private String ddl; 
	
	
	@Bean /* Sempre que a classe TestConfig for chamada, o método abaixo "instanciaDB" será executado */
	public boolean instanciaDB() {
		
		/* Validando se a ddl está definida como create ou se está como update */
		if(ddl.equals("create")) {
			this.dbService.instanciaDB();
		}
		return false;
	}
}
