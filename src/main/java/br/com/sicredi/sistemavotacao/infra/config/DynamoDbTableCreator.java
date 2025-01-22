package br.com.sicredi.sistemavotacao.infra.config;

import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;

public class DynamoDbTableCreator {

    public CreateTableRequest createTableAssociado() {
        return CreateTableRequest.builder()
                .tableName("associado")
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName("id")
                                .keyType(KeyType.HASH)
                                .build()
                )
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("id") // Define o tipo da chave de partição
                                .attributeType("S") // S = String, N = Number, B = Binary
                                .build()
                )
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(5L) // Capacidade de leitura
                                .writeCapacityUnits(5L) // Capacidade de escrita
                                .build()
                )
                .build();
    }

    public CreateTableRequest createTablePauta() {
        return CreateTableRequest.builder()
                .tableName("pauta")
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName("id")
                                .keyType(KeyType.HASH)
                                .build()
                )
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("id") // Define o tipo da chave de partição
                                .attributeType("S") // S = String, N = Number, B = Binary
                                .build()
                )
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(5L) // Capacidade de leitura
                                .writeCapacityUnits(5L) // Capacidade de escrita
                                .build()
                )
                .build();
    }

    public CreateTableRequest createTableVoto() {
        return CreateTableRequest.builder()
                .tableName("voto")
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName("pautaId")
                                .keyType(KeyType.HASH)
                                .build(),
                        KeySchemaElement.builder()
                                .attributeName("associadoId")
                                .keyType(KeyType.RANGE)
                                .build()
                )
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("pautaId") // Define o tipo da chave de partição
                                .attributeType("S") // S = String, N = Number, B = Binary
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName("associadoId") // Define o atributo cpf
                                .attributeType("S") // S = String
                                .build()
                )
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(5L) // Capacidade de leitura
                                .writeCapacityUnits(5L) // Capacidade de escrita
                                .build()
                )
                .build();
    }
}