package ru.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wallet.domain.Wallet;
import ru.wallet.services.WalletService;

@Controller
public class WalletController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(path = "wallet/{wallet}/delete")
    public String deleteWallet(RedirectAttributes redirectAttributes, @PathVariable("wallet") Long walletId) {

        return walletService.deleteWallet(redirectAttributes, walletId);
    }

    @GetMapping(path = "/wallet")
    public String getNewWallet(Model model, @RequestParam(name = "error", required = false) String error) {

        return walletService.getNewWallet(model, error);
    }

    @PostMapping(path = "/wallet")
    public String createWallet(@ModelAttribute Wallet wallet) {

        return walletService.createWallet(wallet);
    }

    @GetMapping(path = "wallet/{wallet}")
    public String findWallet(RedirectAttributes redirectAttributes, @PathVariable("wallet") Long walletId, Model model,
                             @RequestParam(required = false) String error) {

        return walletService.findWallet(redirectAttributes, walletId, model, error);
    }

    @PostMapping(path = "wallet/{wallet}/edit")
    public String editWallet(@ModelAttribute Wallet wallet, @PathVariable("wallet") Long walletId,
                             RedirectAttributes redirectAttributes) {

        return walletService.editWallet(wallet, walletId, redirectAttributes);
    }

}
