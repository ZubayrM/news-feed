package com.zubayr.newsfeed.converter;

import com.zubayr.newsfeed.dto.NewsCategoryDto;
import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.model.News;
import com.zubayr.newsfeed.model.NewsCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface NewsConverter {

    @Mappings({
            @Mapping( target = "date",  dateFormat = "dd-MM-yyyy"),
            @Mapping( source = "newsCategory", target = "category", qualifiedByName = "getName")
    })
    NewsDto convertToDto(News model);

    @Mappings({
            @Mapping(target = "date", dateFormat = "dd-MM-yyyy"),
            @Mapping(source = "category", target = "newsCategory", qualifiedByName = "getNewsCategory")
    })
    News convertToModel(NewsDto dto);


    @Named("getName")
    static String getName(NewsCategory category){
        return category.getName();
    }

    @Named("getNewsCategory")
    static NewsCategory getNewsCategory(String name){
        return NewsCategory.builder().name(name).build();
    }
}
