package com.zubayr.newsfeed.repository;

import com.zubayr.newsfeed.model.NewsCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCategoryRepository extends PagingAndSortingRepository<NewsCategory, Long > {

    NewsCategory findByName(String value);
}
