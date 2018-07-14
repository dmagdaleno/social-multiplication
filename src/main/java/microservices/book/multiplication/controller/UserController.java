package microservices.book.multiplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/{id}")
	public User getById(@PathVariable("id") Long id) {
		return userRepository.findOne(id);
	}
}
