package microservices.book.multiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public final class Multiplication {
	@Id
	@GeneratedValue
	@Column(name="MULTIPLICATION_ID")
	private Long id;
	
	private final int factorA;
	private final int factorB;
	
	public Multiplication() {
		this(0, 0);
	}

	public Multiplication(final int factorA, final int factorB) {
		this.factorA = factorA;
		this.factorB = factorB;
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
		return String.format("Multiplication: %d X %d", factorA, factorB);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + factorA;
		result = prime * result + factorB;
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
		return true;
	}
	
}
