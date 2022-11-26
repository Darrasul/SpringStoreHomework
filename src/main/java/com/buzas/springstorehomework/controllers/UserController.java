package com.buzas.springstorehomework.controllers;

import com.buzas.springstorehomework.entities.users.UserDto;
import com.buzas.springstorehomework.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/user/api/v1")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final RoleService roleService;
    private final CartService cartService;
    private final LNService lnService;

    @GetMapping
    public ModelAndView getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return new ModelAndView("UsersPage");
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("orders", orderService.showAllByUserId(id));
        model.addAttribute("auth_roles", roleService.findAll());
        Long cartId = cartService.findCartIdByUserId(id);
        model.addAttribute("cart_id", cartId);
        model.addAttribute("cart_items", lnService.showAllByCartId(cartId));
        model.addAttribute("total_cost", lnService.showTotalCostByCartId(cartId));
        return new ModelAndView("UserPage");
    }

    @PostMapping("/update")
    public ModelAndView updateUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        checkUserForErrors(userDto, result);
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return new ModelAndView("UserPage");
        }
        userService.update(userDto, userDto.getId());
        return new ModelAndView("UserPage");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteById(id);
        model.addAttribute("users", userService.findAll());
        return new ModelAndView("UsersPage");
    }

    @GetMapping("/order/{id}")
    public ModelAndView showOrder(@PathVariable("id") long id, Model model) {
        model.addAttribute("orderId", id);
        model.addAttribute("order", orderService.showById(id));
        model.addAttribute("items", orderService.showAllFromOrderById(id));
        return new ModelAndView("OrderPage");
    }

    @PostMapping("/order/deleteItem")
    public ModelAndView deleteItemFromOrder(@RequestParam("orderId") Long orderId,
                                            @RequestParam("itemId") Long itemId, Model model) {
        orderService.removeFromOrder(itemId, orderId);
        model.addAttribute("order", orderService.showById(orderId));
        model.addAttribute("items", orderService.showAllFromOrderById(orderId));
        return new ModelAndView("OrderPage");
    }

    @GetMapping("/new")
    @Secured("ROLE_MainAdmin")
    public ModelAndView showNewUserPage(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("auth_roles", roleService.findAll());
        return new ModelAndView("NewUserPage");
    }

    @PostMapping("/new/create")
    @Secured("ROLE_MainAdmin")
    public ModelAndView createNewUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        checkUserForErrors(userDto, result);
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return new ModelAndView("NewUserPage");
        }
        userService.save(userDto);
        return new ModelAndView("NewUserPage");
    }

    @PostMapping("/cart/delete")
//    @Secured("ROLE_User")
    public ModelAndView deleteItemFromCart(@RequestParam("cartId") Long cartId,
                                           @RequestParam("itemId") Long itemId,
                                           Model model) {
        model.addAttribute("users", userService.findAll());
        cartService.removeProduct(cartId, itemId);
        return new ModelAndView("UsersPage");
    }

    @PostMapping("/cart/add")
//    @Secured("ROLE_User")
    public ModelAndView addItemToCart(@RequestParam("cartId") Long cartId,
                                      @RequestParam("itemId") Long itemId,
                                      Model model) {
        model.addAttribute("users", userService.findAll());
        cartService.addProduct(cartId, itemId);
        return new ModelAndView("UsersPage");
    }

    @PostMapping("/cart/createOrder")
//    @Secured("ROLE_User")
    public ModelAndView createOrder(@RequestParam("cartId") Long cartId,
                                    @RequestParam("userId") Long userId,
                                    @RequestParam("totalCost") BigDecimal totalCost,
                                    Model model) {
        cartService.createOrder(cartId, userId, totalCost);
        model.addAttribute("users", userService.findAll());
        return new ModelAndView("UsersPage");
    }

    private void checkUserForErrors(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result) {
        if (userDto.getUsername().isBlank()) {
            result.addError(new FieldError(userDto.getClass().toString(), "username",
                    "Specify a username"));
        } else if (userDto.getUsername().length() > 25) {
            result.addError(new FieldError(userDto.getClass().toString(), "username",
                    "Username must be shorter than 25 characters"));
        }

        if (userDto.getPassword().isBlank()) {
            result.addError(new FieldError(userDto.getClass().toString(), "password",
                    "Specify a password"));
        } else if (userDto.getPassword().length() < 3 || userDto.getPassword().length() > 50) {
            result.addError(new FieldError(userDto.getClass().toString(), "password",
                    "Password must be longer than 3 characters shorter than 50 characters"));
        }

        if (userDto.getEmail().isBlank()) {
            result.addError(new FieldError(userDto.getClass().toString(), "email",
                    "Specify an email"));
        }
    }
}
