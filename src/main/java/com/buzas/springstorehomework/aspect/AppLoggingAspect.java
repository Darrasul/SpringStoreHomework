package com.buzas.springstorehomework.aspect;

import com.buzas.springstorehomework.entities.products.ProductDto;
import com.buzas.springstorehomework.services.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class AppLoggingAspect {

    private final CartService cartService;
    private final LNService lnService;
    private final OrderService orderService;
    private final ProductService productService;
    private final RoleService roleService;
    private final UserService userService;

//    Сделал только вывод get-методов, дабы случайно не напартачить с базой данных

    @AfterReturning(value = "execution(public * com.buzas.springstorehomework.controllers.MainController.getStatistic(..))",
                    returning = "result")
    public void beforeStatisticGathering(JoinPoint joinPoint, List<String> result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Вызван метод: " + signature);

        StopWatch stopWatchForCartService = new StopWatch();
        stopWatchForCartService.start();
        cartService.findCartByUserId(2L);
        cartService.findCartIdByUserId(2L);
        stopWatchForCartService.stop();
        result.add("CartService time: " + stopWatchForCartService.getTotalTimeMillis() + " ms");

        StopWatch stopWatchForProductService = new StopWatch();
        stopWatchForProductService.start();
        productService.findAllByFilters(null, null, 1, 10);
        productService.findAllByFiltersV2(null, null);
        Optional<ProductDto> productDto = productService.findById(8L);
        stopWatchForProductService.stop();
        result.add("ProductService time: " + stopWatchForProductService.getTotalTimeMillis() + " ms");

        StopWatch stopWatchForLnService = new StopWatch();
        stopWatchForLnService.start();
        lnService.showAll();
        lnService.showAllByCartId(1L);
        lnService.showAllByOrderId(1L);
        lnService.showTotalCostByCartId(1L);
        lnService.showSetFromCartById(1L);
        lnService.showSetFromOrderById(1L);
        lnService.findById(11L);
        lnService.checkRightItem(productDto.orElseThrow(() -> new FindException("No such product with id: 8")));
        lnService.findRightItem(productDto.orElseThrow(() -> new FindException("No such product with id: 8")));
        stopWatchForLnService.stop();
        result.add("LnService time: " + stopWatchForLnService.getTotalTimeMillis() + " ms");

        StopWatch stopWatchForOrderService = new StopWatch();
        stopWatchForOrderService.start();
        orderService.showAll();
        orderService.showById(1L);
        orderService.showAllFromOrderById(1L);
        orderService.showAllByUserId(2L);
        stopWatchForOrderService.stop();
        result.add("LnService time: " + stopWatchForOrderService.getTotalTimeMillis() + " ms");

        StopWatch stopWatchForRoleService = new StopWatch();
        stopWatchForRoleService.start();
        roleService.findAll();
        stopWatchForRoleService.stop();
        result.add("LnService time: " + stopWatchForRoleService.getTotalTimeMillis() + " ms");

        StopWatch stopWatchForUserService = new StopWatch();
        stopWatchForUserService.start();
        userService.findAll();
        userService.findById(2L);
        userService.findUserByUsernameSecure("User");
        stopWatchForUserService.stop();
        result.add("LnService time: " + stopWatchForUserService.getTotalTimeMillis() + " ms");
    }
}
