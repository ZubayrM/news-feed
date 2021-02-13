package com.zubayr.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "news")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private NewsCategory newsCategory;

    @PrePersist
    public void prePersist(){
        if(null == getId())
            setId(UUID.randomUUID().toString());
    }


}
