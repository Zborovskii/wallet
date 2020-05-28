package ru.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wallet.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
