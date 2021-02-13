package com.zubayr.newsfeed.repository;

import com.zubayr.newsfeed.model.NewsCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsCategoryRepository extends PagingAndSortingRepository<NewsCategory, String> {

    //NewsCategory findByName(String value);



    Optional<NewsCategory> findByName(String name);
}
