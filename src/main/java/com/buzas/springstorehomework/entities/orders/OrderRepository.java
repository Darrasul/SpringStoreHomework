package com.buzas.springstorehomework.entities.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {
    @Query(value = """
                        select * from orders o
                        where o.id = :id
            """, nativeQuery = true)
    Optional<Order> findById(Long id);
    @Query(value = """
                        select * from orders o
                        where o.user_id = :id
            """, nativeQuery = true)
    List<Order> showAllByCustomerId(Long id);

    void deleteAllById(Long id);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        delete from lineitems_orders lo
                        where lo.orders_id = :orderId
                        and lo.line_items_id = :lnId
            """, nativeQuery = true)
    void deleteFromOrderByOrderIdAndLNId(Long orderId, Long lnId);
// ##
    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into lineitems_orders (line_items_id, orders_id, amount)
                        values (:lnId, :orderId, :amount);
            """, nativeQuery = true)
    void insertIntoOrderByOrderIdAndLNId(Long lnId, Long orderId, int amount);
// ##
    @Query(value = """
                        select l.price from lineitems l
                        where id = :lnId
            """, nativeQuery = true)
    BigDecimal showPriceOfLN(Long lnId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        update orders
                        set total_cost = total_cost + :additionalPrice
                        where id = :orderId
            """, nativeQuery = true)
    int increaseTotalCostOfOrder(Long orderId, BigDecimal additionalPrice);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        update orders
                        set total_cost = total_cost - :decreasePrice
                        where id = :orderId
            """, nativeQuery = true)
    int decreaseTotalCostOfOrder(Long orderId, BigDecimal decreasePrice);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into users_orders (user_id, orders_id)
                        values (:userId, :orderId)
            """, nativeQuery = true)
    int addOrderToUser(Long orderId, Long userId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into orders (user_id, total_cost, time)
                        values (:userId, :totalCost, :timestamp)
            """, nativeQuery = true)
    void createOrder(Long userId, BigDecimal totalCost, Timestamp timestamp);

    @Query(value = """
                        select * from orders o
                        where o.total_cost = :totalCost
                        and o.user_id = :id
            """, nativeQuery = true)
    Optional<Order> findOrderByTotalCostAndUserId(BigDecimal totalCost, Long id);
}
