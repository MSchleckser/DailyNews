package com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PublisherDao extends CrudRepository<Publisher, Integer> {

	@Query(value = "SELECT * FROM Publisher ORDER BY rand() LIMIT 1", nativeQuery = true)
	public Publisher getRandomPublisher();

	@Query(value = "SELECT count(p) > 0 FROM Publisher p WHERE name = :name")
	public boolean doesPublisherExist(@Param("name") String name);
}
