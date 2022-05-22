package com.johannbandelow.mtgcardapi.user;

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
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    @RequestMapping("/{userEmail}")
    public ResponseEntity<?> getUserByEmail(@PathVariable(value = "") String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        if (user == null)  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado, email: " + userEmail);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
