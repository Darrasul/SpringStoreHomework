package com.buzas.springstorehomework.entities.users;

import com.buzas.springstorehomework.entities.carts.Cart;
import com.buzas.springstorehomework.entities.orders.Order;
import com.buzas.springstorehomework.entities.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @Size(max = 25, message = "username too long")
    @NotBlank(message = "specify the username")
    private String username;

    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "password too long")
    @Size(min = 3, message = "password too short")
    private String password;

    @Column(unique = true)
    @NotBlank
    @Email
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
}
