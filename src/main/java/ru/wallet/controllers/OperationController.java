package ru.wallet.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.dto.OperationDto;
import ru.wallet.services.OperationService;

@Controller
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping(value = "/wallet/{wallet}/operations")
    public String getOperationsByWallet(Model model, @PathVariable("wallet") Long walletId,
                                        RedirectAttributes redirectAttributes) {

        return operationService.getOperationsByWallet(model, walletId, redirectAttributes);
    }

    @GetMapping(value = "/wallet/{wallet}/operation")
    public String getNewOperation(Model model, @PathVariable("wallet") Long walletId,
                                  @RequestParam(name = "error", required = false) String error,
                                  RedirectAttributes redirectAttributes) {

        return operationService.getNewOperation(model, walletId, error, redirectAttributes);
    }

    @PostMapping(value = "/wallet/{wallet}/operation")
    public String saveOperation(@Valid @ModelAttribute OperationDto operation, BindingResult bindingResult,
                                @PathVariable("wallet") Long walletId,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("error", getErrors(bindingResult));
            redirectAttributes.addAttribute("wallet", walletId);
            return "redirect:/wallet/{wallet}/operation";
        }

        return operationService.saveOperation(operation, walletId, redirectAttributes);
    }

    private List<String> getErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
    }

}
