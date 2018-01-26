package microservices.book.multiplication.domain;

public final class Multiplication {
	private final int factorA;
	private final int factorB;
	private final int result;
	
	public Multiplication() {
		this(0, 0);
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + factorA;
		result = prime * result + factorB;
		result = prime * result + this.result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Multiplication other = (Multiplication) obj;
		if (factorA != other.factorA)
			return false;
		if (factorB != other.factorB)
			return false;
		if (result != other.result)
			return false;
		return true;
	}
	
}
