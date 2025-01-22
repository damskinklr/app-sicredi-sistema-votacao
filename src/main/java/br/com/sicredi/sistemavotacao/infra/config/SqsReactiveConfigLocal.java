package br.com.sicredi.sistemavotacao.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Profile(value = "local")
@Configuration
public class SqsReactiveConfigLocal {

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create("http://localhost:9324")) // URL do SQS Local
                .build();
    }
}
