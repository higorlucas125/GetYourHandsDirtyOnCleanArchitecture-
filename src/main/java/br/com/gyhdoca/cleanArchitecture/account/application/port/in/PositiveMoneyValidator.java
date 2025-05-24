package br.com.gyhdoca.cleanArchitecture.account.application.port.in;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveMoneyValidator implements ConstraintValidator<PositiveMoney, Money> {
    @Override
    public boolean isValid(Money value, ConstraintValidatorContext context) {
        return value.isPositive();
    }
}
