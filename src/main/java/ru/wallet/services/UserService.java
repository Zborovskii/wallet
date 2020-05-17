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
import ru.wallet.dto.UserDto;
import ru.wallet.mapper.UserMapper;
import ru.wallet.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    //не должно быть
    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getOne(user.getId());
    }

    public String getUserRoom(Model model, String error) {
        User user = getCurrentUser();
        model.addAttribute("wallets", user.getWallets());
        model.addAttribute("user", user);
        model.addAttribute("error", error);

        return "userRoom";
    }

    public Model getUserRegistrationForm(Model model, String error) {
        model.addAttribute("user", new User());
        model.addAttribute("error", error);

        return model;
    }

    public String createUser(UserDto user, RedirectAttributes redirectAttributes) {

        try {
            isValidUser(user);
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", "This name has already used !");
            return "redirect:/registration";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(userMapper.toUser(user));

        return "redirect:/login";
    }

    private void isValidUser(UserDto user) {
        User foundUser = userRepository.findByName(user.getName());

        if (foundUser != null) {
            throw new RuntimeException("This name has already used !");
        }
    }
}
