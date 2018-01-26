package microservices.book.multiplication.domain;

public final class MultiplicationResultAttempt {
	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt;
	private final boolean correct;
	
	public MultiplicationResultAttempt() {
		this(null, null, -1, false);
	}

	public MultiplicationResultAttempt(final User user, final Multiplication multiplication, 
			final int resultAttempt, final boolean correct) {
		this.user = user;
		this.multiplication = multiplication;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}

	public User getUser() {
		return user;
	}

	public Multiplication getMultiplication() {
		return multiplication;
	}

	public int getResultAttempt() {
		return resultAttempt;
	}
	
	public boolean isCorrect() {
		return correct;
	}

	@Override
	public String toString() {
		return "User: " + user + ", Multiplication: " + multiplication + 
				", Result attempt: " + resultAttempt + ", Correct: " + correct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + ((multiplication == null) ? 0 : multiplication.hashCode());
		result = prime * result + resultAttempt;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		MultiplicationResultAttempt other = (MultiplicationResultAttempt) obj;
		if (correct != other.correct)
			return false;
		if (multiplication == null) {
			if (other.multiplication != null)
				return false;
		} else if (!multiplication.equals(other.multiplication))
			return false;
		if (resultAttempt != other.resultAttempt)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
