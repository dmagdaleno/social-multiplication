package microservices.book.multiplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.service.MultiplicationService;

@RestController
@RequestMapping("/multiplications")
public class MultiplicationController {
	
	private static final Logger log = LoggerFactory.getLogger(MultiplicationController.class);
	
	private final MultiplicationService multiplicationService;
	private final int serverPort;
	
	@Autowired
	public MultiplicationController(final MultiplicationService multiplicationService, 
			@Value("${server.port}") int serverPort) {
		this.multiplicationService = multiplicationService;
		this.serverPort = serverPort;
	}
	
	@GetMapping("/random")
	Multiplication getRandomMultiplication() {
		log.info("Generating a random multiplication from server @ {}", serverPort);
		return multiplicationService.createRandomMultiplication();
	}

}
