package br.com.gyhdoca.cleanArchitecture.account.application.port.in;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account.AccountId;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;

public interface GetAccountBalanceUseCase {
    Money getAccountBalance(GetAccountBalanceQuery query);

    record GetAccountBalanceQuery(AccountId accountId){}
}
