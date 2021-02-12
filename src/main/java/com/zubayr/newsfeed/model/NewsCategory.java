package com.zubayr.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "news_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsCategory {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "newsCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<News> news = new ArrayList<>();

}
