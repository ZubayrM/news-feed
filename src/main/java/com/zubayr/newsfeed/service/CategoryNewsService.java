package com.zubayr.newsfeed.service;

import com.zubayr.newsfeed.dto.CategoryNewsDto;
import com.zubayr.newsfeed.model.CategoryNews;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryNewsService {

    CategoryNewsDto getById(String UUID);

    List<CategoryNewsDto> getAll();

    CategoryNewsDto add(CategoryNewsDto dto);

    void delete(String id);

}
