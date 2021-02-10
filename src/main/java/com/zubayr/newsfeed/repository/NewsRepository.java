package com.zubayr.newsfeed.repository;

import com.zubayr.newsfeed.model.News;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Long> {


    Iterable<News> findAllByName(String value);

    Iterable<News> findAllByTextLike(String value);
}
