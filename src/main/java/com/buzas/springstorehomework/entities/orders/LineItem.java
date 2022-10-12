package com.buzas.springstorehomework.entities.orders;

import com.buzas.springstorehomework.entities.carts.Cart;
import com.buzas.springstorehomework.entities.products.Product;
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
@Table(name = "lineitems")
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, length = 25)
    private String currency;

    @ManyToMany
    private Set<Order> orders;

    @ManyToMany
    private Set<Cart> carts;

    public LineItem(Product product) {
        this.product = product;
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.currency = product.getCurrency();
    }

    public LineItem() {
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
