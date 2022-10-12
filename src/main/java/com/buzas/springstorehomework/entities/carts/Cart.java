package com.buzas.springstorehomework.entities.carts;

import com.buzas.springstorehomework.entities.orders.LineItem;
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
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "carts",
            cascade = CascadeType.MERGE)
    private Set<LineItem> lineItems;

    public Cart() {
    }
}
