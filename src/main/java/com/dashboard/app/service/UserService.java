package com.dashboard.app.service;

import com.dashboard.app.enums.UserRole;
import com.dashboard.app.exception.AppException;
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
        User user3 = User
                .builder()
                .name("Pedro")
                .email("pedro@gmail.com")
                .role(UserRole.ADMIN)
                .build();
        User user4 = User
                .builder()
                .name("Daniel")
                .email("daniel@gmail.com")
                .role(UserRole.USER)
                .build();
        User user5 = User
                .builder()
                .name("Jhon")
                .email("jhon@gmail.com")
                .role(UserRole.USER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found with id: " + id));
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new AppException("User ID is required for update");
        }
        return userRepository.save(user);
    }
}