package br.com.sicredi.sistemavotacao.infra.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private CpfValidation cpfValidation;
    private Voting voting;

    @Data
    public static class CpfValidation {
        private String url;
    }

    @Data
    public static class Voting {
        private Duration defaultSessionDuration;
    }
}
