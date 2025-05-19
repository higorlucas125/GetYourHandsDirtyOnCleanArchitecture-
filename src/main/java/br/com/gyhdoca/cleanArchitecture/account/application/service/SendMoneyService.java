package br.com.gyhdoca.cleanArchitecture.account.application.service;

import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyCommand;
import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyUseCase;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.AccountLock;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.LoadAccountPort;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.UpdateAccountStatePort;
import br.com.gyhdoca.cleanArchitecture.account.domain.Account;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        requireAccountExists(command.getSourceAccountId());
        requireAccountExists(command.getTargetAccountId());
        return false;
    }

    private void requireAccountExists(Account.AccountId sourceAccountId) {
    }
}
