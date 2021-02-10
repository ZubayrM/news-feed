package com.zubayr.newsfeed.service;

import com.zubayr.newsfeed.dto.NewsCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NewsCategoryService {

    ResponseEntity getAll();

    ResponseEntity add(NewsCategoryDto dto);

    ResponseEntity delete(Long id);

}
