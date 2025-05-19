package br.com.gyhdoca.cleanArchitecture.account.application.service;

import br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence.GetAccountBalanceQuery;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.LoadAccountPort;
import br.com.gyhdoca.cleanArchitecture.account.domain.Account;
import br.com.gyhdoca.cleanArchitecture.account.domain.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class GetAccountBalanceService  implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(Account.AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance();
    }
}
