package ru.wallet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.User;
import ru.wallet.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.getOne(user.getId());
    }

    public Model getUserRoom(Model model, String error) {
        User user = getCurrentUser();

        model.addAttribute("wallets", user.getWallets());
        model.addAttribute("userId", user.getId());
        model.addAttribute("error", error);

        return model;
    }

    public Model getUserRegistrationForm(Model model, String error) {
        model.addAttribute("user", new User());
        model.addAttribute("error", error);

        return model;
    }

    public String createUser(User user, RedirectAttributes redirectAttributes) {

        try {
            isValidUser(user);
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", "This name has already used !");
            return "redirect:/registration";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login";
    }

    private void isValidUser(User user) {
        User foundUser = userRepository.findByName(user.getName());

        if (foundUser != null) {
            throw new RuntimeException("This name has already used !");
        }
    }
}
