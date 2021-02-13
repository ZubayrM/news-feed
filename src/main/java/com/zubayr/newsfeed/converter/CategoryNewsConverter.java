package com.zubayr.newsfeed.converter;

import com.zubayr.newsfeed.dto.CategoryNewsDto;
import com.zubayr.newsfeed.model.CategoryNews;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryNewsConverter {

    CategoryNewsDto convertToDto(CategoryNews model);

    CategoryNews convertToModel(CategoryNewsDto dto);
}
