package com.buzas.springstorehomework.entities.carts;

import com.buzas.springstorehomework.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface CartRepository extends JpaRepository<Cart, Long>, QuerydslPredicateExecutor<Cart> {

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into lineitems_carts (carts_id, line_items_id)
                        values (:cartId, :itemId)
""", nativeQuery = true)
    void addItemToCart(Long cartId, Long itemId);
// ##
    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into carts (user_id)
                        values (:userId)
""", nativeQuery = true)
    void createCart(Long userId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        update lineitems_carts lc
                        set lc.amount = (lc.amount - 1)
                        where lc.carts_id = :cartId
                        and lc.line_items_id = :itemId
""", nativeQuery = true)
    void decreaseAmountOfItemFromCart(Long cartId, Long itemId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        update lineitems_carts lc
                        set lc.amount = (lc.amount + 1)
                        where lc.carts_id = :cartId
                        and lc.line_items_id = :itemId
""", nativeQuery = true)
    void increaseAmountOfItemFromCart(Long cartId, Long itemId);

    @Query(value = """
                        select lc.amount from lineitems_carts lc
                        where lc.line_items_id = :itemId
                        and lc.carts_id = :cartId
""", nativeQuery = true)
    int showAmountOfItemInTheCart(Long cartId, Long itemId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        delete from lineitems_carts
                        where line_items_id = :itemId
                        and carts_id = :cartId
""", nativeQuery = true)
    void deleteItemFromCart(Long cartId, Long itemId);
    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        delete from lineitems_carts
                        where carts_id = :cartId
""", nativeQuery = true)
    void deleteAllOfItems(Long cartId);
    @Query(value = """
                        select c.id from carts c
                        where c.user_id = :id
""", nativeQuery = true)
    Long findIdOfTheCartByUserId(Long id);
}
