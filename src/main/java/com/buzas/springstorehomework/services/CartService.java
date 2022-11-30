package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.carts.Cart;
import com.buzas.springstorehomework.entities.carts.CartRepository;
import com.buzas.springstorehomework.entities.orders.LineItem;
import com.buzas.springstorehomework.entities.products.ProductDto;
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
        ProductDto productDto = productService.findById(productId).orElseThrow(()
                -> new FindException("No such product with id:" + productId));
        if (lnService.checkRightItem(productDto)) {
            Long itemId = lnService.findRightItem(productDto).getId();
            if (lnService.checkIfItemExistsInCart(itemId, cartId)) {
                cartRepo.increaseAmountOfItemFromCart(cartId, itemId);
            } else {
                cartRepo.addItemToCart(cartId, itemId);
            }
        } else {
            lnService.createLN(productService.findById(productId).get());
            cartRepo.addItemToCart(cartId, lnService.findRightItem(productDto).getId());
        }
    }
// ##
    public void removeProduct(Long cartId, Long productId) {
        if (cartRepo.showAmountOfItemInTheCart(cartId, productId) > 1) {
            cartRepo.decreaseAmountOfItemFromCart(cartId, productId);
        } else {
            cartRepo.deleteItemFromCart(cartId, productId);
        }
    }
// ##
    public void createOrder(Long cartId, Long userId, BigDecimal totalCost) {
        try {
            Set<LineItem> items = lnService.showSetFromCartById(cartId);
            UserDto userDto = userService.findById(userId);
            orderService.createOrder(items, userDto, totalCost);
            cartRepo.deleteAllOfItems(cartId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
// ##
    public Long findCartIdByUserId(Long id) {
        if (cartRepo.findIdOfTheCartByUserId(id) == null){
            cartRepo.createCart(id);
        }
        return cartRepo.findIdOfTheCartByUserId(id);
    }
// ##
    public Optional<Cart> findCartByUserId(Long id) {
        return cartRepo.findById(cartRepo.findIdOfTheCartByUserId(id));
    }
}
