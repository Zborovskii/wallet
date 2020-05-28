package ru.wallet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.Operation;
import ru.wallet.domain.Wallet;
import ru.wallet.dto.OperationDto;
import ru.wallet.enums.Category;
import ru.wallet.repository.WalletRepository;

@Service
public class OperationService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CalculationService calculationService;

    public String getOperationsByWallet(Model model, Long walletId, RedirectAttributes redirectAttributes) {

        Wallet wallet = walletRepository.getOne(walletId);

        model.addAttribute("operations", wallet.getOperations());
        return "operations";
    }

    public String getNewOperation(Model model, Long walletId, String error, RedirectAttributes redirectAttributes) {
        model.addAttribute("error", error);

        model.addAttribute("walletId", walletId);
        model.addAttribute("operation", new Operation());
        model.addAttribute("categories", Category.values());

        return "operation";
    }

    public String saveOperation(OperationDto operation, Long walletId,
                                RedirectAttributes redirectAttributes) {
        Wallet wallet = walletRepository.getOne(walletId);

        return calculationService.makeCalculations(operation, wallet, redirectAttributes);
    }
}
