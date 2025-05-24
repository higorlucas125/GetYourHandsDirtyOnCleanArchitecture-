package br.com.gyhdoca.cleanArchitecture.account.application.port.out;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);
}
