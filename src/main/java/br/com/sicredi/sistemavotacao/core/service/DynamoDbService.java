package br.com.sicredi.sistemavotacao.core.service;

import br.com.sicredi.sistemavotacao.infra.config.DynamoDbTableCreator;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

@Service
public class DynamoDbService {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    private final SqsAsyncClient sqsAsyncClient;

    public DynamoDbService(DynamoDbAsyncClient dynamoDbAsyncClient,
                           SqsAsyncClient sqsAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public boolean criarTabelas() {
        DynamoDbTableCreator creatorAssociado = new DynamoDbTableCreator();
        CreateTableRequest requestAssociado = creatorAssociado.createTableAssociado();

        dynamoDbAsyncClient.createTable(requestAssociado)
                .thenAccept(response -> System.out.println("Tabela criada com sucesso: " + response.tableDescription().tableName()))
                .exceptionally(ex -> {
                    System.err.println("Erro ao criar tabela: " + ex.getMessage());
                    return null;
                });

        DynamoDbTableCreator creatorPauta = new DynamoDbTableCreator();
        CreateTableRequest requestPauta = creatorPauta.createTablePauta();

        dynamoDbAsyncClient.createTable(requestPauta)
                .thenAccept(response -> System.out.println("Tabela criada com sucesso: " + response.tableDescription().tableName()))
                .exceptionally(ex -> {
                    System.err.println("Erro ao criar tabela: " + ex.getMessage());
                    return null;
                });

        DynamoDbTableCreator creatorVoto = new DynamoDbTableCreator();
        CreateTableRequest requestVoto = creatorVoto.createTableVoto();

        dynamoDbAsyncClient.createTable(requestVoto)
                .thenAccept(response -> System.out.println("Tabela criada com sucesso: " + response.tableDescription().tableName()))
                .exceptionally(ex -> {
                    System.err.println("Erro ao criar tabela: " + ex.getMessage());
                    return null;
                });

        criarFila();

        return true;

    }

    private void criarFila() {
        String queueName = "sqs-sistema-votacao-resultados.fifo";
        sqsAsyncClient.createQueue(CreateQueueRequest.builder().queueName(queueName).build())
                .thenAccept(response -> {
                    System.out.println("Queue created: " + response.queueUrl());
                });
    }
}
