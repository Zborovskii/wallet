package ru.wallet.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import ru.wallet.domain.Wallet;
import ru.wallet.enums.Category;

@Data
public class OperationDto {

    private Wallet wallet;

    @NotNull
    private Integer amount;

    @NotNull
    private LocalDateTime created;

    @NotNull
    private Category category;
}
