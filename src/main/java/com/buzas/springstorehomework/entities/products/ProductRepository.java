package com.buzas.springstorehomework.entities.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    @Query(value = """
                    select * from products p
                    where p.id = :id
            """, nativeQuery = true)
    Optional<Product> findById(Long id);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                    delete from products p
                    where p.id = :id
            """, nativeQuery = true)
    void deleteById(Long id);

    @Query(value = """
                    select * from products p
                    where (:maximumFilter is null or p.price <= :maximumFilter)
                    and (:minimumFilter is null or p.price >= :minimumFilter)
            """, countQuery = """
                    select count(*) from products p
                    where (:maximumFilter is null or p.price <= :maximumFilter)
                    and (:minimumFilter is null or p.price >= :minimumFilter)
            """, nativeQuery = true
    )
    Page<Product> findAllByFilters(Double maximumFilter, Double minimumFilter, Pageable pageable);

    @Query(value = """
                    select * from products p
                    where (:maximumFilter is null or p.price <= :maximumFilter)
                    and (:minimumFilter is null or p.price >= :minimumFilter)
            """, nativeQuery = true
    )
    List<Product> findAllByFiltersV2(Double maximumFilter, Double minimumFilter);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                    update products p
                    set p.currency = :currency,
                    p.description = :desc,
                    p.price = :price,
                    p.title = :title
                    where p.id = :productId
            """, nativeQuery = true)
    int updateProduct(Long productId, String currency,
                      String desc, BigDecimal price, String title);

    @Query(value = """
                    select * from products p
            """, nativeQuery = true
    )
    List<Product> findAllForWeb();
}
