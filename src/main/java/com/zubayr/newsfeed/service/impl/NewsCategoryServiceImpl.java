package com.zubayr.newsfeed.service.impl;

import com.zubayr.newsfeed.converter.NewsCategoryConverter;
import com.zubayr.newsfeed.dto.NewsCategoryDto;
import com.zubayr.newsfeed.model.NewsCategory;
import com.zubayr.newsfeed.repository.NewsCategoryRepository;
import com.zubayr.newsfeed.service.NewsCategoryService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    private NewsCategoryRepository newsCategoryRepository;
    private NewsCategoryConverter newsCategoryConverter;

    @Autowired
    public NewsCategoryServiceImpl(NewsCategoryRepository newsCategoryRepository) {
        this.newsCategoryRepository = newsCategoryRepository;
        newsCategoryConverter = Mappers.getMapper(NewsCategoryConverter.class);
    }

    @Override
    public ResponseEntity getAll() {
        Iterable<NewsCategory> iterable = newsCategoryRepository.findAll(Sort.by("name"));
        ArrayList<NewsCategoryDto> dtoList = new ArrayList<>();
        iterable.forEach( o-> dtoList.add(newsCategoryConverter.convertToDto(o)) );
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity add(NewsCategoryDto dto) {
        NewsCategory save = newsCategoryRepository.save(newsCategoryConverter.convertToModel(dto));
        return ResponseEntity.ok(newsCategoryConverter.convertToDto(save));
    }

    @Override
    public ResponseEntity delete(UUID id) {
        if (newsCategoryRepository.findById(id).isPresent()) {
            newsCategoryRepository.deleteById(id);
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }
}
