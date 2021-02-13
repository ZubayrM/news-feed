package com.zubayr.newsfeed.controller;

import com.zubayr.newsfeed.dto.CategoryNewsDto;
import com.zubayr.newsfeed.service.CategoryNewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category_news")
public class CategoryNewsController {

    private final CategoryNewsService categoryNewsService;

    public CategoryNewsController(@RequestBody CategoryNewsService categoryNewsService) {
        this.categoryNewsService = categoryNewsService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryNewsDto>> getAll(){
        return ResponseEntity.ok(categoryNewsService.getAll());
    }

    @PostMapping
    public ResponseEntity<CategoryNewsDto> add(@RequestBody CategoryNewsDto dto){
        return ResponseEntity.ok(categoryNewsService.add(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        categoryNewsService.delete(id);
        return ResponseEntity.ok().body(null);
    }

}

