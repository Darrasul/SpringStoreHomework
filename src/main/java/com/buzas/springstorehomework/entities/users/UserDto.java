package com.buzas.springstorehomework.entities.users;

import com.buzas.springstorehomework.entities.carts.Cart;
import com.buzas.springstorehomework.entities.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;

    @Size(max = 25, message = "username too long")
    @NotBlank(message = "specify the username")
    private String username;

    @Size(max = 50, message = "password too long")
    @Size(min = 3, message = "password too short")
    private String password;

    @NotBlank
    @Email
    private String email;

    private Cart cart;

    private Set<Role> roles;

    public UserDto() {
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", cart=" + cart +
                ", roles=" + roles +
                '}';
    }
}
