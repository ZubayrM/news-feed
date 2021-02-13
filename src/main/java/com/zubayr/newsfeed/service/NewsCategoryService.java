package com.zubayr.newsfeed.service;

import com.zubayr.newsfeed.dto.NewsCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsCategoryService {

    List<NewsCategoryDto> getAll();

    NewsCategoryDto add(NewsCategoryDto dto);

    void delete(String id);

}
