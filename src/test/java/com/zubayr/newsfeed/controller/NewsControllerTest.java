package com.zubayr.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.model.News;
import com.zubayr.newsfeed.model.NewsCategory;
import com.zubayr.newsfeed.repository.NewsCategoryRepository;
import com.zubayr.newsfeed.repository.NewsRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
class NewsControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsCategoryRepository newsCategoryRepository;

    private static final String testId = UUID.randomUUID().toString();
    private final News testNews = News.builder()
            .id(testId)
            .name("test name")
            .text("test text")
            .date(LocalDate.now())
            .build();

    private final static String testCategoryId = UUID.randomUUID().toString();
    private final NewsCategory testCategory = NewsCategory.builder()
            .id(testCategoryId)
            .name("test category")
            .build();

    @BeforeEach
    void setUp() {
        newsRepository.deleteAll();
        NewsCategory saveCategory = newsCategoryRepository.save(testCategory);
        testNews.setNewsCategory(saveCategory);
        newsRepository.save(testNews);
    }

    @AfterEach
    void tearDown() {
        newsRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void getAll() {
        mvc.perform(get("/news"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is(testNews.getName())))
                .andExpect(jsonPath("$.[0].category", is(testCategory.getName())));
    }

    @Test
    @SneakyThrows
    void search() {
        mvc.perform(get("/news/search")
                .param("type", "name")
                .param("value", testNews.getName()))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(get("/news/search")
                .param("type", "category")
                .param("value", testNews.getNewsCategory().getName()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void add() {

        NewsDto dto = NewsDto.builder()
                .id(UUID.randomUUID().toString())
                .name("new test name")
                .text("new test text")
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                category(testCategory.getName()).build();


        mvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(dto.getName())));
    }

    @Test
    @SneakyThrows
    void update() {
        String testTest = "other text";
        News news1 = newsRepository.findById(testId).orElse(null);

        Optional<News> news = newsRepository.findById(testNews.getId());
        news.ifPresent(n-> {
            n.setText(testTest);
            try {
                mvc.perform(put("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(n)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.text", is(testTest)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @SneakyThrows
    void deleteById() {
        mvc.perform(delete("/news/"+ testId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}