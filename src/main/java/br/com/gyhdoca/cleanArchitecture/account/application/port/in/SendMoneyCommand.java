package br.com.gyhdoca.cleanArchitecture.account.application.port.in;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;
import org.antlr.v4.runtime.misc.NotNull;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account.AccountId;

import static br.com.gyhdoca.cleanArchitecture.account.common.validation.Validation.validate;


public record SendMoneyCommand(
        @NotNull AccountId sourceAccountId,
        @NotNull AccountId targetAccountId,
        @NotNull @PositiveMoney Money money
) {

    public SendMoneyCommand(
            AccountId sourceAccountId,
            AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        validate(this);
    }

}