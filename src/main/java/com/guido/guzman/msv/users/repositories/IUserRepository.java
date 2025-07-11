package com.guido.guzman.msv.users.repositories;

import com.guido.guzman.msv.users.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
