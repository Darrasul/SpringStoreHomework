package com.buzas.springstorehomework.controllers;

import com.buzas.springstorehomework.entities.roles.Role;
import com.buzas.springstorehomework.entities.users.UserDto;
import com.buzas.springstorehomework.services.OrderService;
import com.buzas.springstorehomework.services.RoleService;
import com.buzas.springstorehomework.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/api/v1")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final RoleService roleService;

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
        return new ModelAndView("UserPage");
    }

    @PostMapping("/update")
    public ModelAndView updateUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return new ModelAndView("UserPage");
        }
        userService.update(userDto, userDto.getId());
        return new ModelAndView("UserPage");
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
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return new ModelAndView("NewUserPage");
        }
        userService.save(userDto);
        return new ModelAndView("NewUserPage");
    }
}
