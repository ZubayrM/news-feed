package com.zubayr.newsfeed.service.impl;

import com.zubayr.newsfeed.converter.NewsCategoryConverter;
import com.zubayr.newsfeed.dto.NewsCategoryDto;
import com.zubayr.newsfeed.model.NewsCategory;
import com.zubayr.newsfeed.repository.NewsCategoryRepository;
import com.zubayr.newsfeed.service.NewsCategoryService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<NewsCategoryDto> getAll() {
        Iterable<NewsCategory> iterable = newsCategoryRepository.findAll(Sort.by("name"));
        List<NewsCategoryDto> dtoList = new ArrayList<>();
        iterable.forEach(o-> dtoList.add(newsCategoryConverter.convertToDto(o)));
        return dtoList;
    }

    @Override
    public NewsCategoryDto add(NewsCategoryDto dto) {
        NewsCategory save = newsCategoryRepository.save(newsCategoryConverter.convertToModel(dto));
        return newsCategoryConverter.convertToDto(save);
    }

    @Override
    public void delete(String id) {
        newsCategoryRepository.deleteById(id);
    }
}
