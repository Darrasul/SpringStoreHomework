package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.orders.LineItem;
import com.buzas.springstorehomework.entities.orders.Order;
import com.buzas.springstorehomework.entities.orders.OrderRepository;
import com.buzas.springstorehomework.entities.users.User;
import com.buzas.springstorehomework.entities.users.UserDto;
import com.buzas.springstorehomework.entities.users.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final UserDtoMapper mapper;
    @Autowired
    private final UserService userService;

    public List<Order> showAll() {
        return orderRepo.findAll();
    }

    public List<Order> showAllByUserId(Long id) {
        return orderRepo.showAllByCustomerId(id);
    }

    public void createOrder(Set<LineItem> items, UserDto userDto) {
        orderRepo.saveAndFlush(new Order(mapper.map(userDto), items));
    }

    public void addToOrder(LineItem item, Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new FindException("No such order with id:" + id));
        order.addToOrder(item);
        User customer = order.getUser();
        orderRepo.deleteById(id);
        orderRepo.saveAndFlush(new Order(customer, order.getLineItems()));
    }

    public void removeFromOrder(LineItem item, Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new FindException("No such order with id:" + id));
        order.deleteFromOrder(item);
        User customer = order.getUser();
        orderRepo.deleteById(id);
        orderRepo.saveAndFlush(new Order(customer, order.getLineItems()));
    }

    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }
}
