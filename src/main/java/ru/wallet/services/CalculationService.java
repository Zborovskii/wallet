package ru.wallet.services;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.Wallet;
import ru.wallet.dto.OperationDto;
import ru.wallet.enums.Category;
import ru.wallet.mapper.OperationMapper;
import ru.wallet.repository.OperationRepository;

@Service
public class CalculationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationMapper operationMapper;

    public String makeCalculations(OperationDto operation, Wallet wallet, RedirectAttributes redirectAttributes) {

        if (operation.getCategory().equals(Category.REFILL)) {
            wallet.setBalance(wallet.getBalance() + operation.getAmount());
            operation.setCreated(LocalDateTime.now());
            operation.setWallet(wallet);
            operationRepository.save(operationMapper.toOperation(operation));

            return "redirect:/user-room";

        } else if (wallet.getBalance() < operation.getAmount() || wallet.getLimit() < operation.getAmount()) {
            redirectAttributes.addAttribute("error", "You don't have enough funds or limit");
            return "redirect:/wallet/{wallet}/operation";
        }

        wallet.setBalance(wallet.getBalance() - operation.getAmount());
        wallet.setLimit(wallet.getLimit() - operation.getAmount());
        operation.setCreated(LocalDateTime.now());
        operation.setWallet(wallet);
        operationRepository.save(operationMapper.toOperation(operation));

        return "redirect:/user-room";
    }
}
