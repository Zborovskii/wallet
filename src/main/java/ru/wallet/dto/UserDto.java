package ru.wallet.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
