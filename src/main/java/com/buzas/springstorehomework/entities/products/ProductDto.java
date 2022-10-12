package com.buzas.springstorehomework.entities.products;

import com.buzas.springstorehomework.entities.orders.LineItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @Size(min = 3, message = "title too short")
    @Size(max = 50, message = "title too long")
    @NotBlank(message = "specify the title")
    private String title;

    @Size(max = 250, message = "description too long")
    private String description;

    @NotNull(message = "specify the price")
    @DecimalMin(value = "0.00", message = "price too low")
    private BigDecimal price;

    @NotBlank(message = "specify the currency")
    @Size(max = 25, message = "name of currency is too long")
    private String currency;

    private Set<LineItem> lineItems;

    public ProductDto() {
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
