package br.com.gyhdoca.cleanArchitecture.account.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class ActivityWindow {

    private List<Activity> activities;


    public LocalDateTime getStartTimestamp(){
        return activities.stream()
                .min(Comparator.comparing(Activity::getTimestamp))
    }

    public Money calculateBalance(Account.AccountId id ){
        return null;
    }
}
