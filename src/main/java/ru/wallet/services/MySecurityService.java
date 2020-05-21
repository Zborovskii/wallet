package ru.wallet.services;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.repository.WalletRepository;

@Component
public class MySecurityService {

    private WalletRepository walletRepository;

    public MySecurityService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public boolean hasWalletOwnerPermission(Object principal, Long walletId) {
        User user = (User) principal;
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        boolean result = false;

        if (wallet.isPresent()) {
            result = wallet.get().getOwner().getId().equals(user.getId());
        }

        return result;
    }

    public boolean hasWalletUserPermission(Authentication authentication, Long walletId) {
        User user = (User) authentication.getPrincipal();
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        boolean result = false;

        if (wallet.isPresent()) {
            result = wallet.get().getUsers().stream().anyMatch(item -> item.getId().equals(user.getId()));
        }

        return result;
    }
}
