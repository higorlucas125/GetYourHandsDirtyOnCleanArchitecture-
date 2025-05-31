package br.com.gyhdoca.cleanArchitecture.account.addapter.in.web;

import br.com.gyhdoca.cleanArchitecture.IntegrationTest;

import br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence.AccountPersistenceAdapter;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Account;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class SendMoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountPersistenceAdapter accountPersistenceAdapter;

    @Test
    @Description("Should return is OK and value")
    void IntialTestIntegrationWithContainerTest() throws Exception {

        mockMvc.perform(post("/accounts/send/{sourceAccount}/{targetAccoun}/{amount}",
                        1L, 2L, 500L)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());

        Account source = accountPersistenceAdapter.loadAccount(new Account.AccountId(1L), LocalDateTime.now());
        Account target = accountPersistenceAdapter.loadAccount(new Account.AccountId(1L), LocalDateTime.now());

        assertEquals(Money.of(500), target.calculateBalance());
        assertEquals(Money.of(500), source.calculateBalance());


    }


}
