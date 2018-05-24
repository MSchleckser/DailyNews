package com.dailynews.DailyNews.models.user;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	@Size(max = 15, message = "Username must be less than 15 characters long. Sorry :(")
	private String username;

	@NotNull
	@Size(min = 8, max = 32, message = "Password must be more than 8 characters long and less than 32 characters long. Sorry :(")
	private String password;

	public User() {
	}

	public User(User other){
		this.id = other.id;
		this.username = other.username;
		this.password = other.password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(username, user.username) &&
				Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {

		return Objects.hash(username, password);
	}
}
