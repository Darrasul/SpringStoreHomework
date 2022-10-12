package com.buzas.springstorehomework.entities.orders;

import com.buzas.springstorehomework.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    public Order() {
    }

    public Order(User user, Set<LineItem> lineItems) {
        this.user = user;
        this.lineItems = lineItems;
    }

    public void addToOrder(LineItem item) {
        lineItems.add(item);
    }

    public void deleteFromOrder(LineItem item) {
        lineItems.remove(item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", lineItems=" + lineItems +
                '}';
    }
}
