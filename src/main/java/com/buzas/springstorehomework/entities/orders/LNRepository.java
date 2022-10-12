package com.buzas.springstorehomework.entities.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LNRepository extends JpaRepository<LineItem, Long>, QuerydslPredicateExecutor<LineItem> {

    @Query(value = """
        select * from lineitems l
        inner join lineitems_orders lo on l.id = lo.line_items_id
        where lo.orders_id = :id
""",nativeQuery = true
    )
    List<LineItem> showAllByOrderId(Long id);
}
