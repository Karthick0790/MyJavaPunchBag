package com.example.redisDemo;

import com.example.redisDemo.model.User;
import com.example.redisDemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class RedisDemoApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDemoApplication.class);
	private final UserRepository userRepository;

	@Autowired
	public RedisDemoApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Saving users. Current user count is " + userRepository.count());
		User karthick = new User("Karthick", 2000);
		User ajith = new User("Ajith", 29000);
		User vijay = new User("Vijay", 55000);

		userRepository.save(karthick);
		userRepository.save(ajith);
		userRepository.save(vijay);
		LOGGER.info("Saved users now. " + userRepository.findAll());
	}
}
