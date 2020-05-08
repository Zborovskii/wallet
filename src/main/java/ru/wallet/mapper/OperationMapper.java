package ru.wallet.mapper;

import org.mapstruct.Mapper;
import ru.wallet.domain.Operation;
import ru.wallet.dto.OperationDto;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    Operation toOperation(OperationDto operationDto);
}
