package br.com.gyhdoca.cleanArchitecture.account.application.port.out;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {
    Account loadAccount(AccountId accountId, LocalDateTime now);
}
