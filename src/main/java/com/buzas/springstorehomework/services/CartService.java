package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.carts.Cart;
import com.buzas.springstorehomework.entities.carts.CartRepository;
import com.buzas.springstorehomework.entities.orders.LineItem;
import com.buzas.springstorehomework.entities.products.ProductDto;
import com.buzas.springstorehomework.entities.users.User;
import com.buzas.springstorehomework.entities.users.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final LNService lnService;

    public void addProduct(Long cartId, Long productId) {
        LineItem lineItem;
        ProductDto productDto = productService.findById(productId).orElseThrow(()
                -> new FindException("No such product with id:" + productId));
        if (lnService.checkRightItem(productDto)) {
            cartRepo.addItemToCart(cartId, lnService.findRightItem(productDto).getId());
        } else {
            lineItem = lnService.createLN(productService.findById(productId).get());
            cartRepo.addItemToCart(cartId, lineItem.getId());
        }
    }

    public void removeProduct(Long cartId, Long productId) {
        cartRepo.deleteItemFromCart(cartId, productId);
    }

    public void createOrder(Long cartId, Long userId, BigDecimal totalCost) {
        try {
            Set<LineItem> items = lnService.showSetFromOrderById(cartId);
            UserDto userDto = userService.findById(userId);
            orderService.createOrder(items, userDto, totalCost);
            cartRepo.deleteAllOfItems(cartId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Long findCartIdByUserId(Long id) {
        return cartRepo.findIdOfTheCartByUserId(id);
    }

    public Optional<Cart> findCartByUserId(Long id) {
        return cartRepo.findById(cartRepo.findIdOfTheCartByUserId(id));
    }
}
