package com.buzas.springstorehomework.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class MainController {

//    http://localhost:8080/SpringStore/swagger-ui/index.html

    @GetMapping
    public ModelAndView getMainPage() {
        return new ModelAndView("MainPage");
    }

    @GetMapping("/statistic")
    public List<String> getStatistic() {
        return new ArrayList<>();
    }
}
