package br.com.sicredi.sistemavotacao.infra.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsConfig {
    private String region;
    private Credentials credentials;
    private DynamoDB dynamodb;
    private SQS sqs;

    @Data
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @Data
    public static class DynamoDB {
        private String endpoint;
        private boolean createTables;
        private Tables tables;
    }

    @Data
    public static class Tables {
        private String agenda;
        private String vote;
        private String associate;
    }

    @Data
    public static class SQS {
        private String endpoint;
        private Queues queues;
    }

    @Data
    public static class Queues {
        private String votingResult;
    }
}
