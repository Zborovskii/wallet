package ru.wallet.services;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.Operation;
import ru.wallet.domain.Wallet;
import ru.wallet.enums.Category;
import ru.wallet.repository.OperationRepository;
import ru.wallet.repository.WalletRepository;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private WalletRepository walletRepository;

    public String getOperationsByWallet(Model model, Long walletId) {
        //        User user = userService.getCurrentUser();

        Wallet wallet = walletRepository.getOne(walletId);

        //        if (isUsersWallet(wallet)) {
        if (true) {
            model.addAttribute("operations", wallet.getOperations());
            return "operations";
        } else {
            return "redirect:/user-room";
        }
    }

    public String getNewOperation(Model model, Long walletId, String error) {
        model.addAttribute("error", error);

        try {
            checkWalletExistence(walletId);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/user-room";
        }

        model.addAttribute("walletId", walletId);
        model.addAttribute("operation", new Operation());

        return "operation";
    }

    public String saveOperation(Operation operation, Long walletId, Model model,
                                RedirectAttributes redirectAttributes) {
        Wallet wallet = walletRepository.getOne(walletId);

        try {
            checkWalletExistence(walletId);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());

            return "redirect:/wallet/{wallet}/operation";
        }

        return makeCalculations(operation, wallet, redirectAttributes);
    }

    private void checkWalletExistence(Long walletId) {
        if (!operationRepository.existsById(walletId)) {
            throw new RuntimeException(String.format("There is no such walletId: %s", walletId));
        }
    }

    private String makeCalculations(Operation operation, Wallet wallet, RedirectAttributes redirectAttributes) {

        if (operation.getCategory().equals(Category.REFILL)) {
            wallet.setBalance(wallet.getBalance() + operation.getAmount());
            operation.setCreated(LocalDateTime.now());
            operation.setWallet(wallet);
            operationRepository.save(operation);

            return "redirect:/user-room";

        } else if (wallet.getBalance() < operation.getAmount() || wallet.getLimit() < operation.getAmount()) {
            redirectAttributes.addAttribute("error", "You don't have enough funds or limit");
            return "redirect:/wallet/{wallet}/operation";
        }

        wallet.setBalance(wallet.getBalance() - operation.getAmount());
        wallet.setLimit(wallet.getLimit() - operation.getAmount());
        operation.setCreated(LocalDateTime.now());
        operation.setWallet(wallet);
        operationRepository.save(operation);

        return "redirect:/user-room";
    }

    //    private Boolean isUsersWallet(Wallet wallet) {
    //        User user = userService.getCurrentUser();
    //
    //        return user.getWallets().stream()
    //            .map(Wallet::getUsers)
    //            .flatMap(Collection::stream)
    //            .anyMatch(u -> u.getId().equals(wallet.getId()));
    //    }


}
