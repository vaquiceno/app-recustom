package com.dashboard.app.service;

import com.dashboard.app.enums.UserRole;
import com.dashboard.app.model.User;
import com.dashboard.app.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        User user1 = User
                .builder()
                .name("Juan")
                .email("juan@gmail.com")
                .role(UserRole.USER)
                .build();
        User user2 = User
                .builder()
                .name("María")
                .email("maria@gmail.com")
                .role(UserRole.ADMIN)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}