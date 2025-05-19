package br.com.gyhdoca.cleanArchitecture.account.application.port.out;

import br.com.gyhdoca.cleanArchitecture.account.domain.Account;
import br.com.gyhdoca.cleanArchitecture.account.domain.Account.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {
    Account loadAccount(AccountId accountId, LocalDateTime now);
}
