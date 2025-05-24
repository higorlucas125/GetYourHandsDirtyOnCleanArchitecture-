package br.com.gyhdoca.cleanArchitecture.account.application.domain.service;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.service.exception.ThresholdExceededException;
import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyCommand;
import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyUseCase;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.AccountLock;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.LoadAccountPort;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.UpdateAccountStatePort;
import br.com.gyhdoca.cleanArchitecture.account.common.UseCase;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account.AccountId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@UseCase
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        checkThreshold(command);
        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);
        Account sourceAccount = loadAccountPort.loadAccount(
                command.sourceAccountId(),
                baselineDate
        );

        Account targetAccount = loadAccountPort.loadAccount(
                command.targetAccountId(),
                baselineDate
        );

        AccountId sourceAccountId = sourceAccount.getId().orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        AccountId targetAccountId = targetAccount.getId().orElseThrow(() ->  new IllegalStateException("expected source account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if(!sourceAccount.withdraw(command.money(),targetAccountId)){
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if(!targetAccount.withdraw(command.money(),sourceAccountId)){
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);

        return true;
    }

    private void checkThreshold( SendMoneyCommand command){
        if(command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
        }
    }

}
