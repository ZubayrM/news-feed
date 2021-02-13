package com.zubayr.newsfeed.controller;

import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAll(){
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> search(@RequestParam String type,@RequestParam String value){
        return ResponseEntity.ok(newsService.search(type, value));
    }

    @PostMapping
    public ResponseEntity<NewsDto> add(@RequestBody NewsDto dto){
        return ResponseEntity.ok(newsService.add(dto));
    }

    @PutMapping
    public ResponseEntity<NewsDto> update(@RequestBody NewsDto dto){
        return ResponseEntity.ok(newsService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        newsService.delete(id);
        return ResponseEntity.ok().body(null);
    }
}
