package ru.wallet.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.services.UserService;
import ru.wallet.validation.WalletBelongsToUser;

public class WalletBelongsToUserValidator implements ConstraintValidator<WalletBelongsToUser, Long> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(WalletBelongsToUser constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long walletId, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.getCurrentUser();

        return user.getWallets().stream()
            .map(Wallet::getId)
            .anyMatch(w -> w.equals(walletId));
    }
}
