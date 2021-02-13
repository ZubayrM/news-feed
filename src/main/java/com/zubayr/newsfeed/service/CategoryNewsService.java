package com.zubayr.newsfeed.service;

import com.zubayr.newsfeed.dto.CategoryNewsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryNewsService {

    List<CategoryNewsDto> getAll();

    CategoryNewsDto add(CategoryNewsDto dto);

    void delete(String id);

}
