package br.com.sicredi.sistemavotacao.infra.persistence.entities;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

import java.time.LocalDateTime;

@Setter
@DynamoDbBean
public class VotoEntity {
    private String id;
    private String agendaId;
    private String associateId;
    private String option;
    private LocalDateTime timestamp;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "agenda-index")
    @DynamoDbAttribute("agendaId")
    public String getAgendaId() {
        return agendaId;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "associate-index")
    @DynamoDbAttribute("associateId")
    public String getAssociateId() {
        return associateId;
    }

    @DynamoDbAttribute("option")
    public String getOption() {
        return option;
    }

    @DynamoDbAttribute("timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
