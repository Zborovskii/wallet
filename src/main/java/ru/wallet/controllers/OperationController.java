package ru.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.Operation;
import ru.wallet.services.OperationService;

@Controller
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping(value = "/wallet/{wallet}/operations")
    public String getOperationsByWallet(Model model, @PathVariable("wallet") Long walletId) {

        return operationService.getOperationsByWallet(model, walletId);
    }

    @GetMapping(value = "/wallet/{wallet}/operation")
    public String getNewOperation(Model model, @PathVariable("wallet") Long walletId,
                                  @RequestParam(name = "error", required = false) String error) {

        return operationService.getNewOperation(model, walletId, error);
    }

    @PostMapping(value = "/wallet/{wallet}/operation")
    public String saveOperation(@ModelAttribute Operation operation, @PathVariable("wallet") Long walletId, Model model,
                                RedirectAttributes redirectAttributes) {

        return operationService.saveOperation(operation, walletId, model, redirectAttributes);
    }


}
