package com.zubayr.newsfeed.converter;

import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.model.CategoryNews;
import com.zubayr.newsfeed.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface NewsConverter {

    @Mappings({
            @Mapping( target = "date",  dateFormat = "dd-MM-yyyy"),
            @Mapping( source = "categoryNews", target = "category", qualifiedByName = "getName")
    })
    NewsDto convertToDto(News model);

    @Mappings({
            @Mapping(target = "date", dateFormat = "dd-MM-yyyy"),
            @Mapping(source = "category", target = "categoryNews", qualifiedByName = "getCategoryNews")
    })
    News convertToModel(NewsDto dto);


    @Named("getName")
    static String getName(CategoryNews category){
        return category.getName();
    }

    @Named("getCategoryNews")
    static CategoryNews getNewsCategory(String name){
        return CategoryNews.builder().name(name).build();
    }
}
