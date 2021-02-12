package com.zubayr.newsfeed.service;

import com.zubayr.newsfeed.dto.NewsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface NewsService {

    ResponseEntity getAll();

    ResponseEntity search(String type, String value);

    ResponseEntity add(NewsDto dto);

    ResponseEntity update(NewsDto dto);

    ResponseEntity delete(UUID id);
}
