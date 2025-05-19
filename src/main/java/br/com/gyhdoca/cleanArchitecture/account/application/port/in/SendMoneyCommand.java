package br.com.gyhdoca.cleanArchitecture.account.application.port.in;

import br.com.gyhdoca.SelfValidating;
import br.com.gyhdoca.cleanArchitecture.account.domain.Money;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;
import br.com.gyhdoca.cleanArchitecture.account.domain.Account.AccountId;


@Getter
public class SendMoneyCommand  extends SelfValidating<SendMoneyCommand> {
    @NotNull
    private final AccountId sourceAccountId;
    @NotNull
    private final AccountId targetAccountId;
    @NotNull
    private final Money money;


    public SendMoneyCommand(
            AccountId sourceAccountId,
            AccountId targetAccountId,
            Money money
    ) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        requireGreaterThan(money, 0);
        this.validateSelf();

    }

    private void requireGreaterThan(Money money, int i) {

    }

}
