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
//  Я в курсе существования изначальных методов findById и deleteById, но, по какой-то причине, после переноса проекта,
//  они начали выдавать ошибки, ведущие прямиком к CrudRepository. Т.е. по какой-то причине ошибку начали выдавать их методы.
//    UPD. нашёл ошибку и, убив на нее час, решил обойти пока что простым путем. Ошибка выглядит следующим образом
//    Failed to introspect Class [org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor] from ClassLoader [jdk.internal.loader.ClassLoaders$AppClassLoader@6f94fa3e]; nested exception is java.lang.IllegalStateException: Failed to introspect Class [org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor] from ClassLoader [jdk.internal.loader.ClassLoaders$AppClassLoader@6f94fa3e]
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
}
