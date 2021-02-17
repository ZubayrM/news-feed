package com.zubayr.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto implements Serializable {

    private String id;

    private String name;

    private String text;

    private String date;

    private String category;

}
