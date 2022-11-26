package com.buzas.springstorehomework.entities.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface NewsRepository extends JpaRepository<News, Long>, QuerydslPredicateExecutor<News> {
    @Query(value = """
                select * from news n
                order by n.id desc
                limit 3
""", nativeQuery = true)
    List<News> findLastThreeNews();

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                insert into news(news.text, news.title)
                values (:text, :title)
""", nativeQuery = true)
    void addNewNews(String title, String text);
}
