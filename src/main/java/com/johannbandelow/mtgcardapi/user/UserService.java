package com.johannbandelow.mtgcardapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){
        List<User> users = (List<User>) userRepository.findAll();
        return users;
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

        return userRepository.save(user);
    }
}
