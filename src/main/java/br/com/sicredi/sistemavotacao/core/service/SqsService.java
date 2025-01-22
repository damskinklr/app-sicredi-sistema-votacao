package br.com.sicredi.sistemavotacao.core.service;

import br.com.sicredi.sistemavotacao.core.domain.ResultadoVotacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsService {

    private static final Logger log = LoggerFactory.getLogger(SqsService.class);

    private final String endpoint;
    private final SqsAsyncClient sqsClient;
    private final ObjectMapper mapper;

    public SqsService(@Value("${aws.sqs.queues.sqs-sistema-votacao-resultados}") final String endpoint,
                      SqsAsyncClient sqsClient,
                      final ObjectMapper mapper) {
        this.endpoint = endpoint;
        this.sqsClient = sqsClient;
        this.mapper = mapper;
    }

    public Mono<Void> sendVoteResult(ResultadoVotacao resultadoVotacao) {
        log.info("Iniciando o envio da mensagem pra fila={}", resultadoVotacao);
        final String message;
        try {
            message = mapper.writeValueAsString(resultadoVotacao);
        } catch (JsonProcessingException e) {
            log.error("Falha ao converter objeto em Json, erro={0}", e);
            return Mono.error(new RuntimeException("Erro ao serializar o resultado da votação para JSON.", e));
        }

        log.info("Payload que será enviado pra fila={}", message);

        return Mono.fromFuture(sqsClient.sendMessage(SendMessageRequest.builder()
                        .queueUrl(endpoint)
                        .messageBody(message)
                        .messageGroupId(resultadoVotacao.pautaId().toString())
                        .messageDeduplicationId(resultadoVotacao.pautaId().toString())
                        .build())
                .thenAccept(response -> log.info("Mensagem enviada com sucesso, MessageId={}", response.messageId()))
                .exceptionally(throwable -> {
                    log.error("Erro ao enviar mensagem para a fila SQS, erro={}", throwable.getMessage());
                    return null;
                }));
    }
}
