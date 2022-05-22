package com.johannbandelow.mtgcardapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User createUser(User user) {

        if (user == null) {
            return null;
        }

        if (user.getName() == null || user.getName().isBlank()) {
            return null;
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return null;
        }

        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());

        if (userByEmail.isPresent()) {
            return null;
        }

        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
