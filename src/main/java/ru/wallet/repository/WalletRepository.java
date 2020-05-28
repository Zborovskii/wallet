package ru.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wallet.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
