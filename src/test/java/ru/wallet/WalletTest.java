package ru.wallet;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.wallet.domain.Operation;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.enums.Category;
import ru.wallet.repository.OperationRepository;
import ru.wallet.repository.UserRepository;
import ru.wallet.repository.WalletRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WalletTest {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Test
    public void testCreateAndFindUser() {
        User savedUser = createNewUser();
        Optional<User> userFromRepository = userRepository.findById(savedUser.getId());

        Assert.assertNotNull(userFromRepository.get());
        Assert.assertEquals(userFromRepository.get().getName(), "userName");
        Assert.assertEquals(userFromRepository.get().getPassword(), "password");
    }

    @Test
    public void testCreateAndFindWallet() {
        User savedUser = createNewUser();
        Wallet savedWallet = createNewWallet(savedUser);

        Optional<Wallet> walletFromRepository = walletRepository.findById(savedWallet.getId());

        Assert.assertNotNull(walletFromRepository.get());
        Assert.assertEquals(walletFromRepository.get().getName(), "walletName");
        Assert.assertEquals(walletFromRepository.get().getBalance().toString(), "1000");
        Assert.assertEquals(walletFromRepository.get().getLimit().toString(), "1000");
        Assert.assertEquals(walletFromRepository.get().getOwner().getName(), "userName");
        Assert.assertNotNull(walletFromRepository.get().getId());

    }

    @Test
    public void testCreateAndFindOperation() {
        User savedUser = createNewUser();
        Wallet savedWallet = createNewWallet(savedUser);
        Operation savedOperation = createNewOperation(savedWallet);

        Optional<Operation> operationFromRepository = operationRepository.findById(savedOperation.getId());

        Assert.assertNotNull(operationFromRepository.get());
        Assert.assertEquals(operationFromRepository.get().getAmount().toString(), "100");
        Assert.assertEquals(operationFromRepository.get().getCategory().toString(), "MEAL");
        Assert.assertNotNull(operationFromRepository.get().getCreated().toString());
        Assert.assertNotNull(operationFromRepository.get().getWallet().toString());
    }

    private User createNewUser() {
        User user = new User();
        user.setName("userName");
        user.setPassword("password");
        return userRepository.save(user);
    }

    private Operation createNewOperation(Wallet wallet) {
        Operation operation = new Operation();
        operation.setWallet(wallet);
        operation.setCategory(Category.MEAL);
        operation.setAmount(100);
        operation.setCreated(LocalDateTime.now());
        return operationRepository.save(operation);
    }

    private Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setLimit(1000);
        wallet.setName("walletName");
        wallet.setBalance(1000);
        wallet.setUsers(Arrays.asList(user));
        wallet.setOwner(user);
        return walletRepository.save(wallet);
    }
}

