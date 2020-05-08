package ru.wallet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.dto.WalletDto;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    Wallet toWallet(WalletDto walletDto);

    @Mapping(target = "owner", source = "user")
    @Mapping(target = "name", source = "walletDto.name")
    @Mapping(target = "id", ignore = true)
    Wallet toWalletWithOwner(WalletDto walletDto, User user);

}
