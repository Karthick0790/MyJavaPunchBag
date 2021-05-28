package com.example.redisDemo.controller;

import com.example.redisDemo.model.User;
import com.example.redisDemo.repository.UserRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 12000")
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId) {
        LOGGER.info("Getting user " + userId);
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @CachePut(value = "users", key = "#userId")
    @PutMapping("/user/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @CacheEvict(value = "users", key = "#id", allEntries = true)
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        LOGGER.info("Delete user " + id);
        userRepository.deleteById(id);
    }
}
