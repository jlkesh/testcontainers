package com.example.testcontainers.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public User create(User user) {
        return userDAO.save(user);
    }

    public User findByEmail(@NonNull String email) {
        Supplier<RuntimeException> exceptionSupplier = () -> new RuntimeException("User with email '%s' not found !!!");
        return userDAO.findByEmail(email).orElseThrow(exceptionSupplier);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }
}