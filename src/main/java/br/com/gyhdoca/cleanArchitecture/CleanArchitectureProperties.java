package br.com.gyhdoca.cleanArchitecture;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cleanarchitecture")
public class CleanArchitectureProperties {
    private long transferThreshold = Long.MAX_VALUE;
}
