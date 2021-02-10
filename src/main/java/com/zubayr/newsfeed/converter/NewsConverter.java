package com.zubayr.newsfeed.converter;

import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.model.News;
import org.mapstruct.Mapper;

@Mapper
public interface NewsConverter {

    NewsDto convertToDto(News model);

    News convertToModel(NewsDto dto);

}
