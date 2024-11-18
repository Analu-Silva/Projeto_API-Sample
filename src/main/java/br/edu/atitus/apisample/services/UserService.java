package br.edu.atitus.apisample.services;

import org.springframework.stereotype.Service;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

import java.util.regex.Pattern;
import java.util.List;
import java.util.regex.Matcher;

//String, cria objetos dessa classe para mim
@Service
public class UserService {
	// Essa classe possuiu uma depenência de um objeto UserRepository
	private final UserRepository repository;
	
    // Expressão regular para validação de e-mails
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    
    // No metódo construtor existe a injeção de dependência
    public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}

	public UserEntity save(UserEntity newUser) throws Exception {
        // Validação de objeto nulo
        if (newUser == null) {
            throw new Exception("Objeto nulo!");
        }
        
        // Validação de nome
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            throw new Exception("Nome Inválido!");
        }
        newUser.setName(newUser.getName().trim());
        
        // Validação de e-mail
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            throw new Exception("E-mail não pode ser vazio!");
        }
        newUser.setEmail(newUser.getEmail().trim());
        
        // Verifica se o e-mail é válido
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(newUser.getEmail());
        
        if (!matcher.matches()) {
            throw new Exception("Formato de e-mail inválido!");
        }
        
        if (repository.existsByEmail(newUser.getEmail()))
        	throw new Exception("Já existe um usuário com esse email!");
        
        // TODO: Invocar método da camada repository
        this.repository.save(newUser);
        
        return newUser;
    }

	public List<UserEntity> findAll() throws Exception {
		return repository.findAll();
	}
}
