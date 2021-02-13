package com.zubayr.newsfeed.repository;

import com.zubayr.newsfeed.model.News;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, String> {


    Iterable<News> findAllByName(String value);

    Iterable<News> findAllByTextLike(String value);
}
