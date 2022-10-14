package com.buzas.springstorehomework.services;

import com.buzas.springstorehomework.entities.orders.Order;
import com.buzas.springstorehomework.entities.roles.Role;
import com.buzas.springstorehomework.entities.users.User;
import com.buzas.springstorehomework.entities.users.UserDto;
import com.buzas.springstorehomework.entities.users.UserDtoMapper;
import com.buzas.springstorehomework.entities.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.module.FindException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final UserDtoMapper mapper;
    private final PasswordEncoder encoder;

    public List<UserDto> findAll() {
        return userRepo.findAllByFilters()
                .stream().map(mapper::map).toList();
    }

    public UserDto findById(long id) {
        return mapper.map(userRepo.findById(id)
                .orElseThrow(() -> new FindException("No such user with id:" + id)));
    }

    public org.springframework.security.core.userdetails.User findUserByUsernameSecure(String username) {
        return userRepo.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList())
                )).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public void save(UserDto userDto) {
        try {
            userRepo.addUser(userDto.getEmail(), userDto.getPassword(), userDto.getUsername());
            User user = userRepo.findUserByUsername(userDto.getUsername())
                    .orElseThrow(() -> new FindException("No such user with username:" + userDto.getUsername()));
            for (Role role : userDto.getRoles()) {
                addRoleById(role.getId(), user.getId());
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void update(UserDto userDto, Long id) {
        userRepo.updateUserById(id, userDto.getEmail(), userDto.getPassword(), userDto.getUsername());
    }

    public void addRoleById(Long userId, Long roleId) {
        userRepo.addUserRolesById(userId, roleId);
    }

    public void removeRoleById(Long userId, Long roleId) {
        userRepo.deleteUserRoleByUserIdAndRoleId(userId, roleId);
    }

    public void deleteById(long id) {
        userRepo.deleteById(id);
    }

}
