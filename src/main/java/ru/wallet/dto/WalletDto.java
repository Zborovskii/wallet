package ru.wallet.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import ru.wallet.domain.Operation;
import ru.wallet.domain.User;

@Data
public class WalletDto {

    @NotNull
    private String name;

    private List<User> users;

    @Min(value = 0, message = "The value of \"balance\" must be positive")
    private Integer balance;

    @Min(value = 0, message = "The value of \"limit\" must be positive")
    private Integer limit;

    private User owner;

    private List<Operation> operations;

}
