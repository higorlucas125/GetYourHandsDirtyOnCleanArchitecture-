package br.com.gyhdoca.cleanArchitecture.account.application.domain.service.exception;

import br.com.gyhdoca.cleanArchitecture.account.application.domain.model.Money;

public class ThresholdExceededException extends RuntimeException {
    public ThresholdExceededException(Money threshold, Money actual){
        super(String.format("Maximum threshold for transferring money exceeded: treid to transfer %s but threshold is %s! ", actual,threshold));
    }

    public ThresholdExceededException(String message) {
        super(message);
    }
}
