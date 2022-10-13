package com.buzas.springstorehomework.entities.orders;

import com.buzas.springstorehomework.entities.users.User;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany(mappedBy = "orders",
                cascade = CascadeType.MERGE)
    private Set<LineItem> lineItems;

    private BigDecimal totalCost;

    public Order() {
    }

    public Order(User user, Set<LineItem> lineItems) {
        this.user = user;
        this.lineItems = lineItems;
    }

//    Явное прописывание из-за расчетов на стороне сервиса: на товар в будущем может действовать скидка, в таком случае
//    изначальная цена, указанная в БД будет отличаться от реальной, т.к. скидка может быть начислена по разным причинам:
//    кроме сезонных скидок бывают, к примеру, скидки по купонам
    public Order(User user, Set<LineItem> lineItems, BigDecimal totalCost) {
        this.user = user;
        this.lineItems = lineItems;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", lineItems=" + lineItems +
                '}';
    }
}
