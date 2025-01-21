package br.com.sicredi.sistemavotacao.infra.gateway;

import br.com.sicredi.sistemavotacao.infra.gateway.response.CpfValidationResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class CpfValidationRepository {

    private final WebClient webClient = WebClient.create("https://user-info.herokuapp.com");

    public Mono<Boolean> isEligibleToVote(String cpf) {
        return webClient.get()
                .uri("/users/{cpf}", cpf)
                .retrieve()
                .bodyToMono(CpfValidationResponse.class)
                .map(response -> "ABLE_TO_VOTE".equals(response.status().name()));
    }
}
