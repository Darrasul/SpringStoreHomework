package com.buzas.springstorehomework.controllers;

import com.buzas.springstorehomework.entities.news.News;
import com.buzas.springstorehomework.services.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final NewsService newsService;

//    http://localhost:8080/SpringStore/swagger-ui/index.html

    @GetMapping
    public ModelAndView getMainPage(Model model) {
        model.addAttribute("news", newsService.findLastNews());
        model.addAttribute("item", new News("title", "text"));
        return new ModelAndView("MainPage");

    }

    @PostMapping
    public ModelAndView addNewNews(Model model, @Valid @ModelAttribute("item") News news) {
        newsService.addNewNews(news.getTitle(), news.getText());
        model.addAttribute("news", newsService.findLastNews());
        model.addAttribute("item", new News("title", "text"));
        return new ModelAndView("MainPage");
    }

    @GetMapping("/statistic")
    public List<String> getStatistic() {
        return new ArrayList<>();
    }
}
