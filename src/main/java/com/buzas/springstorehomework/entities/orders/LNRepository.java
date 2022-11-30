package com.buzas.springstorehomework.entities.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface LNRepository extends JpaRepository<LineItem, Long>, QuerydslPredicateExecutor<LineItem> {

    @Query(value = """
                    select l.* from lineitems l
                    inner join lineitems_orders lo on l.id = lo.line_items_id
                    where lo.orders_id = :id
            """, nativeQuery = true
    )
    List<LineItem> showAllByOrderId(Long id);

    @Query(value = """
                    select l.* from lineitems l
                    inner join lineitems_carts lc on l.id = lc.line_items_id
                    where lc.carts_id = :id
            """, nativeQuery = true
    )
    List<LineItem> showAllByCartId(Long id);
    @Query(value = """
                    select l.* from lineitems l
                    inner join lineitems_orders lo on l.id = lo.line_items_id
                    where lo.orders_id = :id
            """, nativeQuery = true
    )
    Set<LineItem> showSetByOrderId(Long id);

    @Query(value = """
                    select l.* from lineitems l
                    inner join lineitems_carts lc on l.id = lc.line_items_id
                    where lc.carts_id = :id
            """, nativeQuery = true
    )
    Set<LineItem> showSetByCartId(Long id);
    @Query(value = """
                    select * from lineitems l
                    where l.title = :title
                    and l.price = :price
                    and l.currency = :currency
""", nativeQuery = true)
    Optional<LineItem> findRightItem(String title, BigDecimal price, String currency);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                    insert into lineitems
                    (currency, price, title, product_id)
                    values
                    (:currency, :price, :title, :productId)
""", nativeQuery = true)
    void addLN(String currency, BigDecimal price, String title, Long productId);

    @Query(value = """
                    select count(*) from lineitems_carts lc
                    where lc.carts_id = :cartId
                    and lc.line_items_id = :itemId
""", nativeQuery = true)
    int findItemByItemIdAndCartId(Long itemId, Long cartId);

}
