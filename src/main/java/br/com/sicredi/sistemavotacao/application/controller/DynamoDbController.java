package br.com.sicredi.sistemavotacao.application.controller;

import br.com.sicredi.sistemavotacao.core.service.DynamoDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dynamo")
@Tag(name = "DynamoDB", description = "Endpoint para criar as tabelas no DynamoDB")
public class DynamoDbController {

    private DynamoDbService dynamoDbService;

    public DynamoDbController(DynamoDbService dynamoDbService) {
        this.dynamoDbService = dynamoDbService;
    }

    @PostMapping("/tabelas")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar tabelas no DynamoDB")
    public ResponseEntity<Boolean> criarTabelas() {
        var response =  dynamoDbService.criarTabelas();
        return ResponseEntity.ok(response);
    }
}
