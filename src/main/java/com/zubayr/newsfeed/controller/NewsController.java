package com.zubayr.newsfeed.controller;

import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity getAll(){
        return newsService.getAll();
    }

    @GetMapping("/search")
    public ResponseEntity search(String type, String value){
        return newsService.search(type, value);
    }

    @PostMapping
    public ResponseEntity add(NewsDto dto){
        return newsService.add(dto);
    }

    @PutMapping
    public ResponseEntity update(NewsDto dto){
        return newsService.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return newsService.delete(id);
    }
}
