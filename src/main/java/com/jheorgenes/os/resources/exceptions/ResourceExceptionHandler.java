package com.jheorgenes.os.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jheorgenes.os.services.exceptions.DataIntegratyViolationException;
import com.jheorgenes.os.services.exceptions.ObjectNotFoundException;

@ControllerAdvice //Define essa anotação para quando houver exceptions, esse controller ser chamado, tratar a exception e devolver um retorno aceitável pro usuário
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class) //Anotation define para chamar esse método assim que surgir uma exception lançada, se for da classe de exception passada como argumento, executará o método abaixo
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e) {
		/* Cria um objeto do tipo StandarError com os argumentos abaixo */
		StandardError error = new StandardError(
				System.currentTimeMillis(), /* Busca o tempo em millisegundos que ocorreu o erro */
				HttpStatus.NOT_FOUND.value(), /* Pega o StatusCode da classe HTTPStatus que seja do tipo NOT_FOUND */
				e.getMessage() /* Pega a mensagem obtida da classe ObjectNotFoundException  */
		);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); /* Retorna a resposta com o StatusCode NOT_FOUND e com o corpo da requisição com o objeto do tipo StandardError */
	}
	
	@ExceptionHandler(DataIntegratyViolationException.class) 
	public ResponseEntity<StandardError> objectNotFoundException(DataIntegratyViolationException e) {
		StandardError error = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage() 
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<StandardError> objectNotFoundException(MethodArgumentNotValidException e) {
		ValidationError error = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro na validação dos campos!");
	
		//e.getBindingResult().getFieldErrors().forEach(x -> error.addError(x.getField(), x.getDefaultMessage()));	
		for(FieldError x : e.getBindingResult().getFieldErrors()) { /* Percorre a lista dos erros (retornada como objeto no postman) */
			error.addError(x.getField(), x.getDefaultMessage()); /* Adicionando a cada linha de erro, os campos e as mensagens obtidas */
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); /* Retorna um status 404 e no corpo do json, as exceptions selecionadas */
	}
}
