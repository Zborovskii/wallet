package ru.wallet.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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

    public String deleteWallet(Long walletId) {
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

    public String findWallet(Long walletId, Model model, String error) {

        model.addAttribute("error", error);
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        model.addAttribute("wallet", wallet.get());

        return "walletEdit";
    }

    public String editWallet(WalletDto walletDto, Long walletId) {

        User user = userService.getCurrentUser();
        Wallet wallet = walletMapper.toWalletWithOwnerAndId(walletDto, user, walletId);
        walletRepository.save(wallet);

        return "redirect:/user-room";
    }
}
