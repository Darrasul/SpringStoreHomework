package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.orders.LineItem;
import com.buzas.springstorehomework.entities.orders.Order;
import com.buzas.springstorehomework.entities.orders.OrderRepository;
import com.buzas.springstorehomework.entities.users.UserDto;
import com.buzas.springstorehomework.entities.users.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final UserDtoMapper mapper;
    private final PasswordEncoder encoder;
    @Autowired
    private final UserService userService;

    public List<Order> showAll() {
        return orderRepo.findAll();
    }

    public Optional<Order> showById(Long id) {
        return orderRepo.findById(id);
    }

    public Set<LineItem> showAllFromOrderById (Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new FindException("No such order with id:" + id))
                .getLineItems();
    }

    public List<Order> showAllByUserId(Long id) {
        return orderRepo.showAllByCustomerId(id);
    }

    public void createOrder(Set<LineItem> items, UserDto userDto, BigDecimal totalPrice) {
        try {
            Order order =
                    orderRepo.saveAndFlush(new Order(mapper.map(userDto, encoder), items, totalPrice));
            orderRepo.addOrderToUser(order.getId(), userDto.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void addToOrder(Long lnId, Long orderId) {
        orderRepo.insertIntoOrderByOrderIdAndLNId(lnId, orderId);
        orderRepo.increaseTotalCostOfOrder(orderId, orderRepo.showPriceOfLN(lnId));
    }

    public void removeFromOrder(Long lnId, Long orderId) {
        orderRepo.deleteFromOrderByOrderIdAndLNId(orderId, lnId);
        orderRepo.decreaseTotalCostOfOrder(orderId, orderRepo.showPriceOfLN(lnId));
    }

    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }
}
