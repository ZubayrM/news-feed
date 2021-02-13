package com.zubayr.newsfeed.repository;

import com.zubayr.newsfeed.model.CategoryNews;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryNewsRepository extends PagingAndSortingRepository<CategoryNews, String> {

    Optional<CategoryNews> findByName(String name);
}
