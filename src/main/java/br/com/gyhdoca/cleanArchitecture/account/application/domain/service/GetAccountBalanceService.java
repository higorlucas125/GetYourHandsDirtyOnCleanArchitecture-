package br.com.gyhdoca.cleanArchitecture.account.application.domain.service;

import br.com.gyhdoca.cleanArchitecture.account.application.port.in.GetAccountBalanceUseCase;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.LoadAccountPort;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class GetAccountBalanceService  implements GetAccountBalanceUseCase {

    private final LoadAccountPort loadAccountPort;


    @Override
    public Money getAccountBalance(GetAccountBalanceQuery query) {
        return loadAccountPort.loadAccount(query.accountId(), LocalDateTime.now()).calculateBalance();
    }
}
