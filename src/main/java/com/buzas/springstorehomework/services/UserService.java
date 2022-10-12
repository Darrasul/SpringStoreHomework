package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.orders.Order;
import com.buzas.springstorehomework.entities.users.User;
import com.buzas.springstorehomework.entities.users.UserDto;
import com.buzas.springstorehomework.entities.users.UserDtoMapper;
import com.buzas.springstorehomework.entities.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final UserDtoMapper mapper;

    public List<UserDto> findAll() {
        return userRepo.findAll()
                .stream().map(mapper::map).toList();
    }

    public UserDto findById(long id) {
        return mapper.map(userRepo.findById(id)
                .orElseThrow(() -> new FindException("No such user with id:" + id)));
    }

    public org.springframework.security.core.userdetails.User findUserByUsernameSecure(String username) {
        return userRepo.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList())
                )).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public void save(UserDto userDto) {
        try {
            userRepo.save(mapper.map(userDto));
            userRepo.flush();
        } catch (OptimisticLockingFailureException e) {
            e.fillInStackTrace();
        }
    }

    public void addOrder(Order order) {
        User user = userRepo.findById(order.getUser().getId()).orElseThrow(() ->
                new FindException("No such user with id:" + order.getUser().getId()));
        user.addOrder(order);
        userRepo.saveAndFlush(user);
    }

    public void deleteOrder(Order order) {
        User user = userRepo.findById(order.getUser().getId()).orElseThrow(() ->
                new FindException("No such user with id:" + order.getUser().getId()));
        user.removeOrder(order);
        userRepo.saveAndFlush(user);
    }

    public void deleteById(long id) {
        userRepo.deleteById(id);
    }

}
