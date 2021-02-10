package com.zubayr.newsfeed.controller;

import com.zubayr.newsfeed.dto.NewsCategoryDto;
import com.zubayr.newsfeed.service.NewsCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news-category")
public class NewsCategoryController {

    private NewsCategoryService newsCategoryService;

    public NewsCategoryController(NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }

    @GetMapping
    public ResponseEntity getAll(){
        return newsCategoryService.getAll();
    }

    @PostMapping
    public ResponseEntity add(NewsCategoryDto dto){
        return newsCategoryService.add(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return newsCategoryService.delete(id);
    }

}

