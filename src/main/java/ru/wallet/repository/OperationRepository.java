package ru.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wallet.domain.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
