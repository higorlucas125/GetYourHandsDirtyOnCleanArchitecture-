package br.com.gyhdoca.cleanArchitecture.account.application.port.out;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;

public interface AccountLock {
    void lockAccount(Account.AccountId accountId);
    void releaseAccount(Account.AccountId accountId);
}
