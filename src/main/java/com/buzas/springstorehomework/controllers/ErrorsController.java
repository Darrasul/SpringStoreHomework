package com.buzas.springstorehomework.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/error")
@Slf4j
@RequiredArgsConstructor
public class ErrorsController {

    @GetMapping("/denied")
    public ModelAndView getDeniedPage() {
        return new ModelAndView("Denied");
    }
}
