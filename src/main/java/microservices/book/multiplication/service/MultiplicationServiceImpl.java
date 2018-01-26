package microservices.book.multiplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {
	
	private RandomGeneratorService randomGeneratorService;
	
	@Autowired
	public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
		this.randomGeneratorService = randomGeneratorService;
	}

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}
	
	@Override
	public MultiplicationResultAttempt checkAttempt(final MultiplicationResultAttempt attempt){
		Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");
		
		boolean correct = attempt.getResultAttempt() == attempt.getMultiplication().getResult();
		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(attempt.getUser(), attempt.getMultiplication(), 
				attempt.getResultAttempt(), correct);
		
		return checkedAttempt;
	}

}
