package br.edu.atitus.apisample.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.atitus.apisample.dtos.SignupDTO;
import br.edu.atitus.apisample.entities.TypeUser;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	// A classe AuthController possui uma dependência de "UserService"
	private UserService service;
	// Injeção de dependência via método construtor
	public AuthController(UserService service) {
		super();
		this.service = service;
	}



	@PostMapping("/signup")
	public ResponseEntity<UserEntity> createNewUser(@RequestBody SignupDTO signup) throws Exception{
		
		// TODO converter DTO2Entity
		UserEntity newUser = new UserEntity();
		BeanUtils.copyProperties(signup, newUser);
		newUser.setType(TypeUser.Common);
		
		// TODO invocar método camada service
		service.save(newUser);
		
		// TODO retornar entidade User
		return ResponseEntity.ok(newUser);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleMethod(Exception ex){
		String msg = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(msg);
	}
}
