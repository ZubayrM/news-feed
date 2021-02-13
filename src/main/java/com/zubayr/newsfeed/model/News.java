package com.zubayr.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "news")
@Data
@ToString
@EqualsAndHashCode
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
    @JsonManagedReference
    private NewsCategory newsCategory;

    @PrePersist
    public void prePersist(){
        if(null == getId())
            setId(UUID.randomUUID().toString());
    }


}
