package microservices.book.multiplication.service;

import java.util.List;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
	
	/**
     * Creates a Multiplication object with two randomly-­generated factors
     * between 11 and 99.
     *
     * @return a Multiplication object with random factors
     */
	Multiplication createRandomMultiplication();
	
	/**
	 * 
	 * @param resultAttempt
	 * @return true if the attempt matches the result of the multiplication, false otherwise
	 */
	MultiplicationResultAttempt checkAttempt(final MultiplicationResultAttempt resultAttempt);
	
	
	/**
	 * Find last 5 attemps from the user with the alias specified
	 * @param alias
	 * @return List of attempts from user
	 */
	List<MultiplicationResultAttempt> getStatsForUser(String alias);

}
