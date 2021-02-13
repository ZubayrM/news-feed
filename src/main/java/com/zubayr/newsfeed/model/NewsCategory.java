package com.zubayr.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "news_category")
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsCategory {

    @Id
    private String id;


    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "newsCategory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<News> news = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if(null == getId())
            setId(UUID.randomUUID().toString());
    }

}
