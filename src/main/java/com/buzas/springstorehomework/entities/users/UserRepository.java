package com.buzas.springstorehomework.entities.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(Transactional.TxType.SUPPORTS)
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByUsername(String username);
    @Query(value = """
                        select * from users u
                        where u.id = :id
            """, nativeQuery = true)
    Optional<User> findById(Long id);

    @Query(value = """
                        select * from users u
                        where u.enabled = 1
            """, nativeQuery = true)
    List<User> findAllByFilters();

    @Query(value = """
                        select u from User u
                        where u.username = :username
""")
    Optional<User> findUserByUsername(String username);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into users (email, password, username)
                        values (:email, :password, :username)
            """, nativeQuery = true)
    void addUser(String email, String password, String username);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        update users u
                        set u.email = :email,
                        u.password = :password,
                        u.username = :username
                        where u.id = :id
            """, nativeQuery = true)
    int updateUserById(Long id, String email, String password, String username);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        insert into roles_users(roles_id, users_id)
                        values (:roleId, :userId)
            """, nativeQuery = true)
    int addUserRolesById(Long userId, Long roleId);

    @Modifying
    @Transactional(Transactional.TxType.REQUIRED)
    @Query(value = """
                        delete from roles_users ru
                        where ru.users_id = :userId and
                        ru.roles_id = :roleId
            """, nativeQuery = true)
    void deleteUserRoleByUserIdAndRoleId(Long userId, Long roleId);
}
