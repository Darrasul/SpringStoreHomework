package com.buzas.springstorehomework.entities.products;

import com.buzas.springstorehomework.entities.orders.LineItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @Size(min = 3, message = "title too short")
    @Size(max = 50, message = "title too long")
    @NotBlank(message = "specify the title")
    private String title;

    @Column(length = 250)
    @Size(max = 250, message = "description too long")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "specify the price")
    @DecimalMin(value = "0.00", message = "price too low")
    private BigDecimal price;

    @Column(nullable = false, length = 25)
    @NotBlank(message = "specify the currency")
    @Size(max = 25, message = "name of currency is too long")
    private String currency;

    @OneToMany(mappedBy = "product",
                cascade = CascadeType.ALL)
    private Set<LineItem> lineItems;

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
