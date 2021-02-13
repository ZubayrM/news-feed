package com.zubayr.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {

    private String id;

    private String name;

    private String text;

    private String date;

    private String category;

}
