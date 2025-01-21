package br.com.sicredi.sistemavotacao.infra.persistence.entities;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Setter
@DynamoDbBean
public class VotoEntity {
    private String pautaId;
    private String associadoId;
    private String opcaoVoto;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("pautaId")
    public String getPautaId() {
        return pautaId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("associadoId")
    public String getAssociadoId() {
        return associadoId;
    }

    @DynamoDbAttribute("voto")
    public String getOpcaoVoto() {
        return opcaoVoto;
    }

}
