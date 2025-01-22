package br.com.sicredi.sistemavotacao.core.service;

import br.com.sicredi.sistemavotacao.infra.config.DynamoDbTableCreator;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;

@Service
public class DynamoDbService {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public DynamoDbService(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
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

        return true;

    }
}
