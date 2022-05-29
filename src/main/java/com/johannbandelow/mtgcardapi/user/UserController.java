package com.johannbandelow.mtgcardapi.user;

import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.exceptions.UserEmailAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(path="/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping("/{userEmail}")
    public ResponseEntity<?> getUserByEmail(@PathVariable(value = "") String userEmail) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(userEmail));
        } catch (NoUserFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
