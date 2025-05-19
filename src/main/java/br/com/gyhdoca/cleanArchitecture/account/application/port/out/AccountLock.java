package br.com.gyhdoca.cleanArchitecture.account.application.port.out;

import br.com.gyhdoca.cleanArchitecture.account.domain.Account;

public interface AccountLock {
    void lockAccount(Account.AccountId accountId);
    void releaseAccount(Account.AccountId accountId);
}
