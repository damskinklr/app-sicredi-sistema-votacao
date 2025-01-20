package br.com.sicredi.sistemavotacao.infra.persistence.entities;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@Setter
@DynamoDbBean
public class AssociadoEntity {

    private String id;
    private String name;
    private String cpf;
    private String statusVoto;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "cpf-index")
    @DynamoDbAttribute("cpf")
    public String getCpf() {
        return cpf;
    }

    @DynamoDbAttribute("statusVoto")
    public String getStatusVoto() {
        return statusVoto;
    }
}
