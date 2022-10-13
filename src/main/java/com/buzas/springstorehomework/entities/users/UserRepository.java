package com.buzas.springstorehomework.entities.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByUsername(String username);

    @Query(value = """
                        select * from users u
                        where u.enabled = 1
            """, nativeQuery = true)
    List<User> findAllByFilters();

    @Modifying
    @Query(value = """
                        insert into users (email, password, username)
                        values (:email, :password, :username)
            """, nativeQuery = true)
    void addUser(String email, String password, String username);

    @Modifying
    @Query(value = """
                        update users u
                        set u.email = :email,
                        u.password = :password,
                        u.username = :username
                        where u.id = :id
            """, nativeQuery = true)
    int updateUserById(Long id, String email, String password, String username);

    @Modifying
    @Query(value = """
                        insert into roles_users(roles_id, users_id)
                        values (:roleId, :userId)
            """, nativeQuery = true)
    int addUserRolesById(Long userId, Long roleId);

    @Modifying
    @Query(value = """
                        delete from roles_users ru
                        where ru.users_id = :userId and
                        ru.roles_id = :roleId
            """, nativeQuery = true)
    void deleteUserRoleByUserIdAndRoleId(Long userId, Long roleId);

    @Query(value = """
                        select * from roles_users ru
                        where ru.users_id = :userId
                        and ru.roles_id = :roleId
""", nativeQuery = true)
    int existsRoleCheck(Long userId, Long roleId);
}
