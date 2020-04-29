package ru.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.wallet.domain.User;
import ru.wallet.repository.UserRepository;

@RestController
public class UserControllerNew {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user1")
    public String createUser(@RequestBody User user) {
        userRepository.save(user);

        return "index.html";
    }

}