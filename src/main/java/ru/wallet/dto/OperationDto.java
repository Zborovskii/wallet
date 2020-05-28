package ru.wallet.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import ru.wallet.domain.Wallet;
import ru.wallet.enums.Category;

@Data
public class OperationDto {

    private Wallet wallet;

    @Min(value = 0, message = "The value of \"amount\" must be positive")
    private Integer amount;

    private LocalDateTime created;

    @NotNull
    private Category category;
}
