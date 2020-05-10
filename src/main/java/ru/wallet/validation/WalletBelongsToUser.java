package ru.wallet.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.wallet.validator.WalletBelongsToUserValidator;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WalletBelongsToUserValidator.class)
public @interface WalletBelongsToUser {

    String message() default "You can't do operation with this wallet ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
