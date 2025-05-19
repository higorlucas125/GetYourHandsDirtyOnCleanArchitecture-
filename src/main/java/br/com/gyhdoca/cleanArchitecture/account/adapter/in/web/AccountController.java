package br.com.gyhdoca.cleanArchitecture.account.adapter.in.web;

import br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence.GetAccountBalanceQuery;
import br.com.gyhdoca.cleanArchitecture.account.application.port.in.SendMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final GetAccountBalanceQuery getAccountBalanceQuery;
    private final ListAccountsQuery listAccountsQuery;
    private final LoadAccounQuery loadAccounQuery;

    private final SendMoneyUseCase sendMoneyUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    @GetMapping("/accounts")
    ResponseEntity<List<AccountResource>> listAccounts (){
        return ResponseEntity.ok();
    }
}
