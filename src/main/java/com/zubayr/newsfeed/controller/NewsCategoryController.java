package com.zubayr.newsfeed.controller;

import com.zubayr.newsfeed.dto.NewsCategoryDto;
import com.zubayr.newsfeed.service.NewsCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news-category")
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;

    public NewsCategoryController(@RequestBody NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<NewsCategoryDto>> getAll(){
        return ResponseEntity.ok(newsCategoryService.getAll());
    }

    @PostMapping
    public ResponseEntity<NewsCategoryDto> add(@RequestBody NewsCategoryDto dto){
        return ResponseEntity.ok(newsCategoryService.add(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        newsCategoryService.delete(id);
        return ResponseEntity.ok().body(null);
    }

}

