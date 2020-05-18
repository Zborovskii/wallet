package ru.wallet.controllers;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.dto.WalletDto;
import ru.wallet.services.WalletService;

@Controller
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PreAuthorize("@mySecurityService.hasWalletPermission(authentication, #walletId)")
    @RequestMapping(path = "wallet/{wallet}/delete")
    public String deleteWallet(@PathVariable("wallet") Long walletId) {

        return walletService.deleteWallet(walletId);
    }

    @GetMapping(path = "/wallet")
    public String getNewWallet(Model model, @RequestParam(name = "error", required = false) String error) {

        return walletService.getNewWallet(model, error);
    }

    @PostMapping(path = "/wallet")
    public String createWallet(@Valid @ModelAttribute("wallet") WalletDto walletDto, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("error", getErrors(bindingResult));
            return "redirect:/wallet";
        }

        return walletService.createWallet(walletDto);
    }

    @PreAuthorize("@mySecurityService.hasWalletPermission(authentication, #walletId)")
    @GetMapping(path = "wallet/{wallet}")
    public String findWallet(@PathVariable("wallet") Long walletId, Model model,
                             @RequestParam(required = false) String error) {

        return walletService.findWallet(walletId, model, error);
    }

    @PreAuthorize("@mySecurityService.hasWalletPermission(authentication, #walletId)")
    @PostMapping(path = "wallet/{wallet}/edit")
    public String editWallet(@Valid @ModelAttribute("wallet") WalletDto walletDto, BindingResult bindingResult,
                             @PathVariable("wallet") Long walletId,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("error", getErrors(bindingResult));
            redirectAttributes.addAttribute("walletId", walletId);
            return "redirect:/wallet/{walletId}";
        }

        return walletService.editWallet(walletDto, walletId);
    }

    private List<String> getErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
    }

}
