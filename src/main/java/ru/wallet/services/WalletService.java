package ru.wallet.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.dto.WalletDto;
import ru.wallet.mapper.WalletMapper;
import ru.wallet.repository.WalletRepository;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletMapper walletMapper;

    public String deleteWallet(RedirectAttributes redirectAttributes, Long walletId) {

        try {
            isValidWallet(walletId);
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/user-room";
        }

        walletRepository.deleteById(walletId);
        return "redirect:/user-room";
    }

    public String getNewWallet(Model model, String error) {
        model.addAttribute("wallet", new Wallet());
        model.addAttribute("error", error);

        return "wallet";
    }

    public String createWallet(WalletDto walletDto) {

        User user = userService.getCurrentUser();

        Wallet wallet = walletMapper.toWalletWithOwner(walletDto, user);
        walletRepository.save(wallet);

        return "redirect:/user-room";
    }

    public String findWallet(RedirectAttributes redirectAttributes, Long walletId, Model model, String error) {

        model.addAttribute("error", error);

        Optional<Wallet> wallet = walletRepository.findById(walletId);

        try {
            isValidWallet(walletId);
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/user-room";
        }

        model.addAttribute("wallet", wallet.get());

        return "walletEdit";
    }

    public String editWallet(WalletDto walletDto, Long walletId, RedirectAttributes redirectAttributes) {

        try {
            isValidWallet(walletId);
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/user-room";
        }

        User user = userService.getCurrentUser();
        Wallet wallet = walletMapper.toWalletWithOwnerAndId(walletDto, user, walletId);
        walletRepository.save(wallet);

        return "redirect:/user-room";
    }

    private void isValidWallet(Long walletId) {
        User user = userService.getCurrentUser();
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            throw new RuntimeException(String.format("There is no wallet %s", walletId));
        } else if (user.getId() != wallet.get().getOwner().getId()) {
            throw new RuntimeException("Only owner can edit/delete wallet");
        }
    }
}
