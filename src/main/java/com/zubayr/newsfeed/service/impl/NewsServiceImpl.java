package com.zubayr.newsfeed.service.impl;

import com.zubayr.newsfeed.converter.NewsCategoryConverter;
import com.zubayr.newsfeed.converter.NewsConverter;
import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.model.News;
import com.zubayr.newsfeed.model.NewsCategory;
import com.zubayr.newsfeed.repository.NewsCategoryRepository;
import com.zubayr.newsfeed.repository.NewsRepository;
import com.zubayr.newsfeed.service.NewsService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsRepository newsRepository;
    private NewsConverter newsConverter;
    private NewsCategoryRepository newsCategoryRepository;
    private NewsCategoryConverter newsCategoryConverter;



    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, NewsCategoryRepository newsCategoryRepository) {
        this.newsRepository = newsRepository;
        this.newsCategoryRepository = newsCategoryRepository;
        newsConverter = Mappers.getMapper(NewsConverter.class);
        newsCategoryConverter = Mappers.getMapper(NewsCategoryConverter.class);
    }

    @Override
    public ResponseEntity getAll() {
        Iterable<News> date = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        if (date.iterator().hasNext()) {
            return ResponseEntity.ok(convertToDtlList(date));
        } else return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity search(String type, String value) {

        switch (type){
            case "category": {
                NewsCategory category = newsCategoryRepository.findByName(value);
                if (category != null) {
                    return ResponseEntity.ok(convertToDtlList(category.getNews()));
                } else ResponseEntity.ok(new ArrayList<>());
            }
            case "name": {
                Iterable<News> allByName = newsRepository.findAllByName(value);
                if (allByName.iterator().hasNext()) {
                    return ResponseEntity.ok(convertToDtlList(allByName));
                } else return ResponseEntity.ok(new ArrayList<>());
            }
            case "text": {
                Iterable<News> allByTextLike = newsRepository.findAllByTextLike(value);
                if (allByTextLike.iterator().hasNext()) {
                    return ResponseEntity.ok(convertToDtlList(allByTextLike));
                } else return ResponseEntity.ok(new ArrayList<>());

            }
            default:{
                return ResponseEntity.ok(newsRepository.findAll(Sort.by(Sort.Direction.DESC, "date")));
            }
        }
    }


    @Override
    public ResponseEntity add(NewsDto dto) {
        newsCategoryRepository.save(newsConverter.convertToModel(dto).getNewsCategory());
        News save = newsRepository.save(newsConverter.convertToModel(dto));
        return ResponseEntity.ok(newsConverter.convertToDto(save));
    }

    @Override
    public ResponseEntity update(NewsDto dto) {
        Optional<News> news = newsRepository.findById(dto.getId());
        news.ifPresent(o-> {
            o.setName(dto.getName());
            o.setText(dto.getText());
            newsRepository.save(o);
        });

        return ResponseEntity.ok(newsConverter.convertToDto(news.orElse(new News())));
    }

    @Override
    public ResponseEntity delete(UUID id) {
        if (newsRepository.findById(id).isPresent()){
            newsRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } else return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }

    private List<NewsDto> convertToDtlList(Iterable<News> list){
        ArrayList<NewsDto> newsList = new ArrayList<>();
        list.forEach(o-> newsList.add(newsConverter.convertToDto(o)));
        return newsList;
    }
}
