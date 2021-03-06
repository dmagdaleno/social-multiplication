package microservices.book.multiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public final class User {
	
	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Long id;
	
	private String alias;
	
	public User() {
		this(null);
	}

	public User(final String alias) {
		this.alias = alias;
	}
	
	public Long getId() {
		return id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
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
		User other = (User) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		return true;
	}
	
}
