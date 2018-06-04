package com.dailynews.DailyNews.models.rssfeeds.rsslink;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RssLinkDao extends CrudRepository<RssLink, Integer> {

	@Query(value = "SELECT * FROM rss_link ORDER BY RAND() LIMIT 1", nativeQuery = true)
	public RssLink getRandom();
}
