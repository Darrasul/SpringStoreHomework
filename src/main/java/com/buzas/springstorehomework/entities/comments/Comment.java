package com.buzas.springstorehomework.entities.comments;

import com.buzas.springstorehomework.entities.products.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String username;

    @Column(length = 500, nullable = false)
    @NotBlank
    private String text;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private Product product;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
