package com.guido.guzman.msv.users.services;

import com.guido.guzman.msv.users.entities.Role;
import com.guido.guzman.msv.users.entities.User;
import com.guido.guzman.msv.users.repositories.IRoleRepository;
import com.guido.guzman.msv.users.repositories.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setRoles(getRoles(user));
        user.setEnabled(true);
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        Optional<User> userDb = this.findById(id);

        return userDb.map(u -> {
            u.setEmail(user.getEmail());
            u.setUsername(user.getUsername());
            if (user.getEnabled() == null) {
                u.setEnabled(true);
            } else {
                u.setEnabled(user.getEnabled());
            }
            u.setRoles(getRoles(user));

            return userRepository.save(u);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        role.ifPresent(roles::add);
        if (user.isAdmin()) {
            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            adminRole.ifPresent(roles::add);
        }
        return roles;
    }
}
