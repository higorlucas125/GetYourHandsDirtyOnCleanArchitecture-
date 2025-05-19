package br.com.gyhdoca.cleanArchitecture.account.adapter.in.web;

import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    void sendMpney(
            @PathVariable("sourceAccount") Long sourceAccountId,
            @PathVariable("targetAccount") Long targetAccountId,
            @PathVariable("amount") Long amount
    ){

    }
}
