package br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence;

import br.com.gyhdoca.cleanArchitecture.account.application.port.out.AccountLock;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class NoOpAccountLock implements AccountLock {
    @Override
    public void lockAccount(Account.AccountId accountId) {

    }

    @Override
    public void releaseAccount(Account.AccountId accountId) {

    }
}
