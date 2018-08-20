package com.dailynews.DailyNews.models.user.salt;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salt")
public class Salt {

	@Id
	private String salt;

	@Override
	public String toString() {
		return salt;
	}
}
