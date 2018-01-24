package microservices.book.multiplication.domain;

public final class User {
	
	private String alias;
	
	public User() {
		this(null);
	}

	public User(final String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Override
	public String toString() {
		return String.format("Alias: %s", alias);
	}
}
