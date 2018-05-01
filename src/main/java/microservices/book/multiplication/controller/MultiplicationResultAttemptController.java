package microservices.book.multiplication.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;

@RestController
@RequestMapping("/results")
public class MultiplicationResultAttemptController {
	private static final Logger log = LoggerFactory.getLogger(MultiplicationResultAttemptController.class);
	
	private final MultiplicationService multiplicationService;
	private final int serverPort;

	@Autowired
	public MultiplicationResultAttemptController(final MultiplicationService multiplicationService,
			@Value("${server.port}") int serverPort) {
		this.multiplicationService = multiplicationService;
		this.serverPort = serverPort;
	}
	
	@PostMapping
	ResponseEntity<MultiplicationResultAttempt> postResult(
			@RequestBody MultiplicationResultAttempt resultAttempt){
	    return ResponseEntity.ok(multiplicationService.checkAttempt(resultAttempt));
	}
	
	@GetMapping("/from/{alias}")
	ResponseEntity<List<MultiplicationResultAttempt>> getUserStats(@PathVariable String alias){
		return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
	}
	
	@GetMapping("/{id}")
	ResponseEntity<MultiplicationResultAttempt> getById(@PathVariable("id") Long id){
		log.info("Retrieving result {} from server @ {}", id, serverPort);
		return ResponseEntity.ok(multiplicationService.getMultiplicationResultAttempt(id));
	}
}
