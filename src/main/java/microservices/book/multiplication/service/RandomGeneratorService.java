package microservices.book.multiplication.service;

public interface RandomGeneratorService {
	
	/**
     * @return a randomly-generated factor between 11 and 99. 
     */
	int generateRandomFactor();

}
