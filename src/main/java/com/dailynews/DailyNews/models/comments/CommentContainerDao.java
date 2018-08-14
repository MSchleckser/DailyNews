package com.dailynews.DailyNews.models.comments;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CommentContainerDao extends CrudRepository<CommentContainer, Integer> {
}
