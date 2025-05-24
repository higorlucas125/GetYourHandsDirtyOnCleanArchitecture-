package br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence;


import br.com.gyhdoca.cleanArchitecture.account.application.port.out.LoadAccountPort;
import br.com.gyhdoca.cleanArchitecture.account.application.port.out.UpdateAccountStatePort;
import br.com.gyhdoca.cleanArchitecture.account.common.PersistenceAdapter;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@PersistenceAdapter
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final SpringDataAccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(Account.AccountId accountId, LocalDateTime now) {
        AccountJpaEntity account = accountRepository.findById(accountId.getValue()).orElseThrow(EntityNotFoundException::new);
        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(accountId.getValue(), now);

        Long withdrawalBalance = activityRepository.getWithdrawalBalanceUntil(
                accountId.getValue(),
                now
        ).orElse(0L);

        Long depositBalance = activityRepository.getDepositBalanceUntil(
                accountId.getValue(),
                now
        ).orElse(0L);

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance
        );
    }

    @Override
    public void updateActivities(Account account) {
        account.getActivityWindow().getActivities().stream()
                .filter(activity -> Objects.nonNull(activity.getId()))
                .forEach( activity -> activityRepository.save(accountMapper.mapToJpaEntity(activity)) );
    }
}
