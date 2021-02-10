package com.zubayr.newsfeed.converter;

import com.zubayr.newsfeed.dto.NewsCategoryDto;
import com.zubayr.newsfeed.model.NewsCategory;
import org.mapstruct.Mapper;

@Mapper
public interface NewsCategoryConverter {

    NewsCategoryDto convertToDto(NewsCategory model);


    NewsCategory convertToModel(NewsCategoryDto dto);
}
