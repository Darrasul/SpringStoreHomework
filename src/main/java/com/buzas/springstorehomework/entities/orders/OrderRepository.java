package com.buzas.springstorehomework.entities.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    @Query(value = """
            select * from orders o
            where o.user_id = :id
""", nativeQuery = true)
    List<Order> showAllByCustomerId(Long id);

    void deleteAllById(Long id);
}
