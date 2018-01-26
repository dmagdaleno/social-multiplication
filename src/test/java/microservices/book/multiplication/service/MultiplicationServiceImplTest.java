package microservices.book.multiplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;

public class MultiplicationServiceImplTest {
	
	@Mock
	private RandomGeneratorService randomGeneratorService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private MultiplicationRepository multiplicationRepository;
	@Mock
	private MultiplicationResultAttemptRepository attemptRepository;

	private MultiplicationService multiplicationService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		multiplicationService = new MultiplicationServiceImpl(randomGeneratorService, userRepository,
				multiplicationRepository, attemptRepository);
	}
	
	@Test
	public void checkCorrectAttemptTest() {
		// Given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt expectedReturn = new MultiplicationResultAttempt(user, multiplication, 3000, true);
		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(multiplicationRepository.findByFactorAAndFactorB(50, 60)).willReturn(Optional.empty());
		
		// When
		MultiplicationResultAttempt checkedAttempt = multiplicationService.checkAttempt(attempt);
		
		// Then
		assertThat(checkedAttempt.isCorrect()).isTrue();
		verify(attemptRepository).save(expectedReturn);
	}
	
	@Test
	public void checkWrongAttemptTest() {
		// Given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3001, false);
		MultiplicationResultAttempt expectedReturn = new MultiplicationResultAttempt(user, multiplication, 3001, false);
		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(multiplicationRepository.findByFactorAAndFactorB(50, 60)).willReturn(Optional.empty());
		
		// When
		MultiplicationResultAttempt checkedAttempt = multiplicationService.checkAttempt(attempt);
		
		// Then
		assertThat(checkedAttempt.isCorrect()).isFalse();
		verify(attemptRepository).save(expectedReturn);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowAnExceptionForCheaters() {
		// Given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3001, true);
		
		// When
		MultiplicationResultAttempt checkedAttempt = multiplicationService.checkAttempt(attempt);
		
		// Then
		System.out.println("Never gets here");
		assertThat(checkedAttempt.isCorrect()).isFalse();
	}
	
	@Test
	public void createRandomMultiplicationTest() {
		// Given
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
		
		// When
		Multiplication multiplication = multiplicationService.createRandomMultiplication();
		
		// Then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		assertThat(multiplication.getResult()).isEqualTo(1500);
	}
	
	@Test
	public void retrieveStatsTest() {
		// Given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt atpt1 = new MultiplicationResultAttempt(user, multiplication, 3001, false);
		MultiplicationResultAttempt atpt2 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		MultiplicationResultAttempt atpt3 = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt atpt4 = new MultiplicationResultAttempt(user, multiplication, 2999, false);
		MultiplicationResultAttempt atpt5 = new MultiplicationResultAttempt(user, multiplication, 2000, false);
		List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(atpt1, atpt2, atpt3, atpt4, atpt5);
		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(multiplicationRepository.findByFactorAAndFactorB(50, 60)).willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc(user.getAlias())).willReturn(latestAttempts);
		
		// When
		List<MultiplicationResultAttempt> latestRestult = multiplicationService.getStatsForUser(user.getAlias());
		
		// Then
		assertThat(latestRestult).isEqualTo(latestAttempts);
	}
}