package ru.wallet.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.wallet.domain.User;
import ru.wallet.domain.Wallet;
import ru.wallet.dto.WalletDto;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "owner", source = "user")
    @Mapping(target = "name", source = "walletDto.name")
    @Mapping(target = "limit", expression = "java(setLimit(walletDto))")
    @Mapping(target = "users", expression = "java(setUsers(walletDto, user))")
    @Mapping(target = "id", ignore = true)
    Wallet toWalletWithOwner(WalletDto walletDto, User user);

    @Mapping(target = "id", source = "walletId")
    @Mapping(target = "owner", source = "user")
    @Mapping(target = "name", source = "walletDto.name")
    @Mapping(target = "limit", expression = "java(setLimit(walletDto))")
    @Mapping(target = "users", expression = "java(setUsers(walletDto, user))")
    Wallet toWalletWithOwnerAndId(WalletDto walletDto, User user, Long walletId);

    default Integer setLimit(WalletDto walletDto) {
        if (walletDto.getLimit() == null) {
            walletDto.setLimit(walletDto.getBalance());
        }

        return walletDto.getLimit();
    }

    default List<User> setUsers(WalletDto wallet, User user) {
        Stream<Long> allWalletUserIds = wallet.getUsers().stream()
            .filter(Objects::nonNull)
            .map(User::getId);

        if (allWalletUserIds.noneMatch(u -> u.equals(user.getId()))) {
            wallet.getUsers().add(user);
        }

        return wallet.getUsers();
    }

}
