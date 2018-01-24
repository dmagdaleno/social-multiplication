package microservices.book.multiplication.domain;

public class ResultResponse {
	private final boolean correct;
	
	public ResultResponse() {
		this(false);
	}
	
	public ResultResponse(final boolean correct) {
		this.correct = correct;
	}
	
	public boolean isCorrect() {
		return correct;
	}
}
