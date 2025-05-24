package br.com.gyhdoca.cleanArchitecture;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;
import br.com.gyhdoca.cleanArchitecture.account.application.domain.service.MoneyTransferProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CleanArchitectureProperties.class)
public class CleanArchitectureConfig {

    @Bean
    public MoneyTransferProperties moneyTransferProperties(CleanArchitectureProperties properties){
        return new MoneyTransferProperties(Money.of(properties.getTransferThreshold()));
    }
}
