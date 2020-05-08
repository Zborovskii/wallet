package ru.wallet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.Operation;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.dto.OperationDto;
import ru.wallet.enums.Category;
import ru.wallet.repository.WalletRepository;

@Service
public class OperationService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CalculationService calculationService;

    public String getOperationsByWallet(Model model, Long walletId, RedirectAttributes redirectAttributes) {

        Wallet wallet = walletRepository.getOne(walletId);

        if (isUsersWallet(walletId)) {
            model.addAttribute("operations", wallet.getOperations());
            return "operations";
        } else {
            redirectAttributes.addAttribute("error",
                                            String.format("you can't do operation with %s walletId", walletId));
            return "redirect:/user-room";
        }
    }

    public String getNewOperation(Model model, Long walletId, String error, RedirectAttributes redirectAttributes) {
        model.addAttribute("error", error);

        if (isUsersWallet(walletId)) {
            model.addAttribute("walletId", walletId);
            model.addAttribute("operation", new Operation());
            model.addAttribute("categories", Category.values());

            return "operation";
        } else {
            redirectAttributes.addAttribute("error",
                                            String.format("you can't do operation with %s walletId", walletId));
            return "redirect:/user-room";
        }
    }

    public String saveOperation(OperationDto operation, Long walletId,
                                RedirectAttributes redirectAttributes) {
        Wallet wallet = walletRepository.getOne(walletId);

        if (isUsersWallet(walletId)) {
            return calculationService.makeCalculations(operation, wallet, redirectAttributes);
        } else {
            redirectAttributes.addAttribute("error",
                                            String.format("you can't do operation with %s walletId", walletId));
            return "redirect:/user-room";
        }
    }

    private boolean isUsersWallet(Long walletId) {
        User user = userService.getCurrentUser();

        return user.getWallets().stream()
            .map(Wallet::getId)
            .anyMatch(w -> w.equals(walletId));
    }
}
