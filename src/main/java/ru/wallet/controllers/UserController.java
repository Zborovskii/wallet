package ru.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.User;
import ru.wallet.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration.html";
    }

    @PostMapping(value = "/user")
    public String createUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("user", user);
        userRepository.save(user);

        redirectAttributes.addAttribute("user", user.getName());

        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String greetingForm(Model model, @RequestParam(name = "user", required = false) String user) {

        User newUser = new User();
        if (user != null) {
            newUser.setName(user);
        }

        model.addAttribute("user", newUser);
        return "login.html";
    }

    @RequestMapping(value = "/user-room", method = RequestMethod.POST)
    public String getUserRoom(@ModelAttribute User user, Model model) {

        if (user == null) {
            return "";
        }

        user = userRepository.findByNameAndPassword(user.getName(), user.getPassword());

        if (user != null) {
            model.addAttribute("wallets", user.getWallets());
        }

        return "userRoom.html";
    }
}
