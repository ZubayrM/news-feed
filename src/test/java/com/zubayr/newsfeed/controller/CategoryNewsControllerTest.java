package com.zubayr.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zubayr.newsfeed.dto.CategoryNewsDto;
import com.zubayr.newsfeed.model.CategoryNews;
import com.zubayr.newsfeed.repository.CategoryNewsRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class CategoryNewsControllerTest {


    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryNewsRepository categoryNewsRepository;

    StopWatch stopWatch;

    private final String url = "/category_news";

    private final static String testId = UUID.randomUUID().toString();
    private final CategoryNews testCategoryNews = CategoryNews.builder()
            .id(testId)
            .name("test category")
            .build();

    @BeforeEach
    void setUp() {
        categoryNewsRepository.deleteAll();
        categoryNewsRepository.save(testCategoryNews);
        stopWatch = new StopWatch();
    }

    @AfterEach
    void tearDown() {
        categoryNewsRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void getById() {
        for (int i = 0; i < 5; i++) {
            stopWatch.start("request getAll-" + i );
            mvc.perform(get(url + "/" + testCategoryNews.getId()))
                    .andDo(print())
                    .andExpect(jsonPath("$.name", is(testCategoryNews.getName())));
            stopWatch.stop();
        }
        Arrays.stream(stopWatch.getTaskInfo()).forEach(o-> {
            System.out.println(o.getTaskName() + " = " + o.getTimeMillis());
        });
    }

    @Test
    @SneakyThrows
    void getAll() {
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is(testCategoryNews.getName())));
    }

    @Test
    @SneakyThrows
    void add() {
        CategoryNewsDto newTestCategory = CategoryNewsDto.builder()
                .name("new test category").build();

        mvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTestCategory)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newTestCategory.getName())));
    }

    @Test
    @SneakyThrows
    void deleteById() {
        mvc.perform(delete(url + "/" + testId))
                .andDo(print())
                .andExpect(status().isOk());
    }


}