package com.buzas.springstorehomework.entities.products;

import com.buzas.springstorehomework.entities.comments.Comment;
import com.buzas.springstorehomework.entities.orders.LineItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String title;

    @Column(length = 250)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, length = 25)
    private String currency;

    @OneToMany(mappedBy = "product",
                cascade = CascadeType.ALL)
    private Set<LineItem> lineItems;

    @OneToMany(mappedBy = "product",
                cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @Column
    private int cartAddCount;

    @Column
    private int orderCount;

    @Column
    private int viewCount;

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
