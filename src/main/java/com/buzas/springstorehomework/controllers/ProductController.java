package com.buzas.springstorehomework.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    @GetMapping()
    public ModelAndView getAllProduct() {
        return new ModelAndView("ProductsPage");
    }
}
