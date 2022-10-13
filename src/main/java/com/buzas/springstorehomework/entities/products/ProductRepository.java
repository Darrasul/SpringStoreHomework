package com.buzas.springstorehomework.entities.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    @Query(value = """
                    select * from product p
                    where (:maximumFilter is null or p.price <= :maximumFilter)
                    and (:minimumFilter is null or p.price >= :minimumFilter)
            """, countQuery = """
                    select count(*) from product p
                    where (:maximumFilter is null or p.price <= :maximumFilter)
                    and (:minimumFilter is null or p.price >= :minimumFilter)
            """, nativeQuery = true
    )
    Page<Product> findAllByFilters(Double maximumFilter, Double minimumFilter, Pageable pageable);

    @Query(value = """
                    select * from product p
                    where (:maximumFilter is null or p.price <= :maximumFilter)
                    and (:minimumFilter is null or p.price >= :minimumFilter)
            """, nativeQuery = true
    )
    List<Product> findAllByFiltersV2(Double maximumFilter, Double minimumFilter);

    @Modifying
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
}
