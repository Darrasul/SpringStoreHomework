package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.news.News;
import com.buzas.springstorehomework.entities.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepo;

    public List<News> findLastNews() {
        return newsRepo.findLastThreeNews();
    }

    public void addNewNews(String title, String text) {
        newsRepo.addNewNews(title, text);
    }
}
