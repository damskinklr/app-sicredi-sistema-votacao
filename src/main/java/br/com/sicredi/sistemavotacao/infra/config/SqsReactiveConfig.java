package br.com.sicredi.sistemavotacao.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Profile(value = "!local")
@Configuration
public class SqsReactiveConfig {

    private final SqsAsyncClient sqsAsyncClient;

    public SqsReactiveConfig() {
        this.sqsAsyncClient = SqsAsyncClient
                .builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }
}
