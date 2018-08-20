package com.dailynews.DailyNews.models.user.salt;

import org.hibernate.annotations.Immutable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Immutable
public interface SaltDao extends CrudRepository<Salt, String> {
}
