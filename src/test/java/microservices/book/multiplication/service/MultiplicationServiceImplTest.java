package microservices.book.multiplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.book.multiplication.domain.Multiplication;

public class MultiplicationServiceImplTest {
	
	@Mock
	private RandomGeneratorService randomGeneratorService;
	
	private MultiplicationService multiplicationService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);
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
}