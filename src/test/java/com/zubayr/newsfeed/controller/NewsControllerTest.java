package com.zubayr.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zubayr.newsfeed.dto.NewsDto;
import com.zubayr.newsfeed.model.News;
import com.zubayr.newsfeed.model.NewsCategory;
import com.zubayr.newsfeed.repository.NewsRepository;
import lombok.SneakyThrows;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    static String testId = UUID.randomUUID().toString();

    @Test
    @SneakyThrows
    void getAll() {
        mvc.perform(get("/news"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void search() {
        mvc.perform(get("/news/search")
                .param("type", "name")
                .param("value", "test name"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void add() {

        NewsDto dto = NewsDto.builder()
                .id(testId)
                .name("test name")
                .text("test text")
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                category("test category").build();


        mvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void update() {
        Optional<News> news = newsRepository.findById(testId);
        news.ifPresent(o-> {
            o.setText("new test text");
            try {
                mvc.perform(put("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(o)))
                        .andDo(print())
                        .andExpect(status().isOk());
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