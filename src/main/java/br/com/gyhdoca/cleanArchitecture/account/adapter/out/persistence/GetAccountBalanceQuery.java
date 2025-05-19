package br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence;

import br.com.gyhdoca.cleanArchitecture.account.domain.Money;
import br.com.gyhdoca.cleanArchitecture.account.domain.Account.AccountId;

public interface GetAccountBalanceQuery {
    Money getAccountBalance (AccountId accountId);
}
