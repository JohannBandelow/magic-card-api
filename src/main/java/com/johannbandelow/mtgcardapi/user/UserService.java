package com.johannbandelow.mtgcardapi.user;

import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.exceptions.UserEmailAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) throws UserEmailAlreadyExists, BadRequestException {
        this.validateUserCreation(user);

        try {
            User userByEmail = this.getUserByEmail(user.getEmail());
        } catch (NoUserFoundException e) {
            return userRepository.save(user);
        }

        throw new UserEmailAlreadyExists(user.getEmail());
    }

    private void validateUserCreation(User user) throws BadRequestException {
        if (user == null) {
            throw new BadRequestException("Usuário não enviado!");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new BadRequestException("Nome do usuário não enviado");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new BadRequestException("Email do usuário não enviado!");
        }
    }

    public User getUserById(Long userId) throws NoUserFoundException, BadRequestException {
        if (userId == null) {
            throw new BadRequestException("ID do usuário não enviado");
        }

        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NoUserFoundException(userId));
    }

    @Transactional
    public User getUserByEmail(String email) throws NoUserFoundException {
        return userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new NoUserFoundException("Nenhum usuário encontrado com email: " + email));
    }
}
