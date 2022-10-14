package com.buzas.springstorehomework.entities.users;

import com.buzas.springstorehomework.entities.carts.Cart;
import com.buzas.springstorehomework.entities.orders.Order;
import com.buzas.springstorehomework.entities.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 25)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    @ColumnDefault(value = "1")
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Order> orders;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", orders=" + orders +
                ", cart=" + cart +
                ", roles=" + roles +
                '}';
    }
}
