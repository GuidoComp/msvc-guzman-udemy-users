package com.guido.guzman.msv.users.services;

import com.guido.guzman.msv.users.entities.User;

import java.util.Optional;

public interface IUserService {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Iterable<User> findAll();
    void delete(Long id);
    Optional<User> update(Long id, User user);
}
