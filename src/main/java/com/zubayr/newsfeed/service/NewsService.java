package com.zubayr.newsfeed.service;

import com.zubayr.newsfeed.dto.NewsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {

    List<NewsDto> getAll();

    List<NewsDto> search(String type, String value);

    NewsDto add(NewsDto dto);

    NewsDto update(NewsDto dto);

    void delete(String id);
}
