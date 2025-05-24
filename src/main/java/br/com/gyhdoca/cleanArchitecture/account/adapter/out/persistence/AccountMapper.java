package br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account.AccountId;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Activity;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Activity.ActivityId;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.ActivityWindow;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper {

    Account mapToDomainEntity (
            AccountJpaEntity account,
            List<ActivityJpaEntity> activities,
            Long withdrawalBalance,
            Long depositBalance
    ){
        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance)
        );

        return Account.withId(
                new AccountId(account.getId()),
                baselineBalance,
                mapToActivityWindow(activities)
        );
    }

    ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activites){
        List<Activity> mappedActivities = new ArrayList<>();

        activites.stream().forEach( ac -> mappedActivities.add(
                new Activity(
                        new ActivityId(ac.getId()),
                        new AccountId(ac.getOwnerAccountId()),
                        new AccountId(ac.getSourceAccountId()),
                        new AccountId(ac.getTargetAccountId()),
                        ac.getLocalDateTime(),
                        Money.of(ac.getAmount())
                )
        ));

        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity){
        return new ActivityJpaEntity (
                activity.getId() == null ? null : activity.getId().getValue(),
                activity.getTimestamp(),
                activity.getOwnerAccountId().getValue(),
                activity.getSourceAccountId().getValue(),
                activity.getTargetAccountId().getValue(),
                activity.getMoney().getAmount().longValue()
        );
    }
}
