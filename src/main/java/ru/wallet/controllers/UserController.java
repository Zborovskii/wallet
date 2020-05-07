package ru.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.User;
import ru.wallet.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/registration")
    public String registrationForm(Model model, @RequestParam(name = "error", required = false) String error) {
        userService.getUserRegistrationForm(model, error);

        return "registration";
    }

    @PostMapping(value = "/user")
    public String createUser(@ModelAttribute @Validated User user, RedirectAttributes redirectAttributes) {

        return userService.createUser(user, redirectAttributes);
    }

    @RequestMapping(value = "/user-room")
    public String getUserRoom(Model model, @RequestParam(name = "error", required = false) String error) {
        model = userService.getUserRoom(model, error);

        return "userRoom";
    }
}
