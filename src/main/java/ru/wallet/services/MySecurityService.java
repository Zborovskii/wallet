package ru.wallet.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;

@Component("mySecurityService")
public class MySecurityService {

    public boolean hasWalletPermission(Authentication authentication, Long walletId) {
        User user = (User) authentication.getPrincipal();

        boolean userHasThisWallet = user.getWallets().stream()
            .map(Wallet::getId)
            .anyMatch(item -> item.equals(walletId));

        boolean userHasOwnWallet = user.getOwnWallets().stream()
            .map(Wallet::getId)
            .anyMatch(item -> item.equals(walletId));

        return userHasOwnWallet && userHasThisWallet;
    }
}
