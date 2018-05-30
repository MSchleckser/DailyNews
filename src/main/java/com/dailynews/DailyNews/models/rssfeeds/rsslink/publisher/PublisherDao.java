package com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface PublisherDao extends CrudRepository<Publisher, Integer> {
}
