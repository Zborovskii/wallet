package ru.wallet.validator;

import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.repository.WalletRepository;
import ru.wallet.services.UserService;
import ru.wallet.validation.ValidWallet;

public class ValidWalletValidator implements ConstraintValidator<ValidWallet, Long> {

    @Autowired
    private UserService userService;
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void initialize(ValidWallet constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long walletId, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.getCurrentUser();
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            return false;
        } else if (user.getId() != wallet.get().getOwner().getId()) {
            return false;
        }
        return true;
    }
}
