package com.buzas.springstorehomework.entities.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 255)
    private String title;

    @Column(length = 510)
    private String text;

    public News() {
    }

    public News(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
