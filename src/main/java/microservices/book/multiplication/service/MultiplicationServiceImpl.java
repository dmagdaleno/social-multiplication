package microservices.book.multiplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {
	
	private RandomGeneratorService randomGeneratorService;
	private UserRepository userRepository;
	private MultiplicationRepository multiplicationRepository;
	private MultiplicationResultAttemptRepository attemptRepository;
	
	@Autowired
	public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService, UserRepository userRepository,
		MultiplicationRepository multiplicationRepository, MultiplicationResultAttemptRepository attemptRepository) {
		
		this.randomGeneratorService = randomGeneratorService;
		this.userRepository = userRepository;
		this.multiplicationRepository = multiplicationRepository;
		this.attemptRepository = attemptRepository;
	}

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}
	
	@Override
	public MultiplicationResultAttempt checkAttempt(final MultiplicationResultAttempt attempt){
		Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct smart ass!!");
		
		Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());
		Optional<Multiplication> multiplication = multiplicationRepository.findByFactorAAndFactorB(
				attempt.getMultiplication().getFactorA(), attempt.getMultiplication().getFactorB());
		
		boolean correct = attempt.getResultAttempt() == attempt.getMultiplication().getResult();
		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(attempt.getUser()), 
				multiplication.orElse(attempt.getMultiplication()), attempt.getResultAttempt(), correct);
		
		attemptRepository.save(checkedAttempt);
		
		return checkedAttempt;
	}

	@Override
	public List<MultiplicationResultAttempt> getStatsForUser(String alias) {
		return attemptRepository.findTop5ByUserAliasOrderByIdDesc(alias);
	}

}
