package com.buzas.springstorehomework.entities.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {

    @Query(value = """
                        select * from comments c
                        where c.product_id = :productId
""", nativeQuery = true)
    List<Comment> findAllByProductId(Long productId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into comments(date, text, username, product_id)
                        values (:date, :text, :username, :productId)
""", nativeQuery = true)
    void addNewComment(Date date, String text, String username, Long productId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        delete from comments c
                        where c.id = :id
""", nativeQuery = true)
    void deleteCommentById(Long id);
}
