package br.com.gyhdoca.cleanArchitecture.account.adapter.in.web;

import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyCommand;
import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyUseCase;
import br.com.gyhdoca.cleanArchitecture.account.common.WebAdapter;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping(path = "/accounts/send/{sourceAccount}/{targetAccount}/{amount}")
    void sendMpney(
            @PathVariable("sourceAccount") Long sourceAccountId,
            @PathVariable("targetAccount") Long targetAccountId,
            @PathVariable("amount") Long amount
    ){
        SendMoneyCommand command = new SendMoneyCommand(
                new Account.AccountId(sourceAccountId),
                new Account.AccountId(targetAccountId),
                Money.of(amount)
        );

        sendMoneyUseCase.sendMoney(command);
    }
}
