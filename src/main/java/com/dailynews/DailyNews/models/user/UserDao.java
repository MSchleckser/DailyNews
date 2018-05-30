package com.dailynews.DailyNews.models.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

	@Query("SELECT id FROM User WHERE " +
			"username = :username " +
			"AND password = :password")
	public Integer getUserId(@Param("username") String username, @Param("password") String password);

	@Query("SELECT id FROM User WHERE " +
			"username = :username ")
	public Integer getUserId(@Param("username") String username);
}
