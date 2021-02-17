package com.zubayr.newsfeed.service.impl;

import com.zubayr.newsfeed.converter.CategoryNewsConverter;
import com.zubayr.newsfeed.dto.CategoryNewsDto;
import com.zubayr.newsfeed.model.CategoryNews;
import com.zubayr.newsfeed.repository.CategoryNewsRepository;
import com.zubayr.newsfeed.service.CategoryNewsService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryNewsServiceImpl implements CategoryNewsService {

    private final CategoryNewsRepository categoryNewsRepository;
    private final CategoryNewsConverter categoryNewsConverter;

    @Autowired
    public CategoryNewsServiceImpl(CategoryNewsRepository categoryNewsRepository) {
        this.categoryNewsRepository = categoryNewsRepository;
        categoryNewsConverter = Mappers.getMapper(CategoryNewsConverter.class);
    }

    @Override
    @Cacheable(cacheNames = "category")
    public CategoryNewsDto getById(String UUID) {
        System.out.println("entered");
        return categoryNewsConverter.convertToDto(categoryNewsRepository.findById(UUID).orElseThrow());
    }

    @Override
    public List<CategoryNewsDto> getAll() {
        Iterable<CategoryNews> iterable = categoryNewsRepository.findAll(Sort.by("name"));
        List<CategoryNewsDto> dtoList = new ArrayList<>();
        iterable.forEach(o-> dtoList.add(categoryNewsConverter.convertToDto(o)));
        return dtoList;
    }

    @Override
    @CachePut(cacheNames = "category")
    public CategoryNewsDto add(CategoryNewsDto dto) {
        CategoryNews save = categoryNewsRepository.save(categoryNewsConverter.convertToModel(dto));
        return categoryNewsConverter.convertToDto(save);
    }

    @Override
    @CacheEvict("category")
    public void delete(String id) {
        categoryNewsRepository.deleteById(id);
    }
}
