package com.zubayr.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zubayr.newsfeed.model.NewsCategory;
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

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
class NewsCategoryControllerTest {


    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @SneakyThrows
    void getAll() {
        mvc.perform(get("/news-category"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void add() {
        NewsCategory category = new NewsCategory(UUID.randomUUID(),"test", new ArrayList<>());

        mvc.perform(post("/news-category")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void deleteById() {
        mvc.perform(delete( "/news-category/"+ UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}