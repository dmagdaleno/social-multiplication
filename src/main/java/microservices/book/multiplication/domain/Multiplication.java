package microservices.book.multiplication.domain;

public class Multiplication {
	private final int factorA;
	private final int factorB;
	private final int result;

	public Multiplication(final int factorA, final int factorB) {
		this.factorA = factorA;
		this.factorB = factorB;
		this.result = factorA * factorB;
	}

	public int getFactorA() {
		return factorA;
	}

	public int getFactorB() {
		return factorB;
	}
	
	public int getResult(){
		return factorA * factorB;
	}
	
	@Override
	public String toString() {
		return String.format("Multiplication: %d X %d = %d", factorA, factorB, result);
	}
}
