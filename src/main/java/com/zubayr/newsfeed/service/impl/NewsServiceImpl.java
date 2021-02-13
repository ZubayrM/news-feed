package com.zubayr.newsfeed.service.impl;

import com.zubayr.newsfeed.converter.NewsConverter;
import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.model.CategoryNews;
import com.zubayr.newsfeed.model.News;
import com.zubayr.newsfeed.repository.CategoryNewsRepository;
import com.zubayr.newsfeed.repository.NewsRepository;
import com.zubayr.newsfeed.service.NewsService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsConverter newsConverter;
    private final CategoryNewsRepository categoryNewsRepository;


    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, CategoryNewsRepository categoryNewsRepository) {
        this.newsRepository = newsRepository;
        this.categoryNewsRepository = categoryNewsRepository;
        newsConverter = Mappers.getMapper(NewsConverter.class);
    }

    @Override
    public List<NewsDto> getAll() {
        Iterable<News> date = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        if (date.iterator().hasNext()) {
            return convertToDtoList(date);
        } else return new ArrayList<>();
    }

    @Override
    public List<NewsDto> search(String type, String value) {

        switch (type) {
            case "category": {
                Optional<CategoryNews> category = categoryNewsRepository.findByName(value);
                return category.map(newsCategory -> convertToDtoList(newsCategory.getNews())).orElseGet(ArrayList::new);
            }
            case "name": {
                Iterable<News> allByName = newsRepository.findAllByName(value);
                if (allByName.iterator().hasNext()) {
                    return convertToDtoList(allByName);
                } else return new ArrayList<>();
            }
            case "text": {
                Iterable<News> allByTextLike = newsRepository.findAllByTextLike(value);
                if (allByTextLike.iterator().hasNext()) {
                    return convertToDtoList(allByTextLike);
                } else return new ArrayList<>();
            }
            default: {
                return  convertToDtoList(newsRepository.findAll(Sort.by(Sort.Direction.DESC, "date")));
            }
        }
    }


    @Override
    public NewsDto add(NewsDto dto) {
        News news = newsConverter.convertToModel(dto);
        CategoryNews categoryNews = news.getCategoryNews();

        Optional<CategoryNews> category = categoryNewsRepository.findByName(categoryNews.getName());

        if (category.isPresent()) {
            news.setCategoryNews(category.get());
        } else {
            CategoryNews newSaveCategory = categoryNewsRepository.save(categoryNews);
            news.setCategoryNews(newSaveCategory);
        }
        News newNews = newsRepository.save(news);

        return newsConverter.convertToDto(newNews);
    }

    @Override
    public NewsDto update(NewsDto dto) {
        Optional<News> news = newsRepository.findById(dto.getId());
        news.ifPresent(o -> {
            o.setName(dto.getName());
            o.setText(dto.getText());
            newsRepository.save(o);
        });

        return newsConverter.convertToDto(news.orElse(new News()));
    }

    @Override
    public void delete(String id) {
        newsRepository.deleteById(id);
    }

    private List<NewsDto> convertToDtoList(Iterable<News> list) {
        ArrayList<NewsDto> newsList = new ArrayList<>();
        list.forEach(o -> newsList.add(newsConverter.convertToDto(o)));
        return newsList;
    }
}
