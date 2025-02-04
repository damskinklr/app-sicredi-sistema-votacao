package br.com.sicredi.sistemavotacao.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Profile(value = "!local")
@Configuration
public class DynamoDbReactiveConfig {

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient(){
        return DynamoDbAsyncClient
                .builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient(DynamoDbAsyncClient dynamoDbAsyncClient) {
        return DynamoDbEnhancedAsyncClient
                .builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build();
    }
}
