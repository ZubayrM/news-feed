package com.zubayr.newsfeed.converter;

import com.zubayr.newsfeed.dto.NewsCategoryDto;
import com.zubayr.newsfeed.model.NewsCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface NewsCategoryConverter {

    NewsCategoryDto convertToDto(NewsCategory model);

    @Named("toNewsCategory")
    NewsCategory convertToModel(NewsCategoryDto dto);
}
