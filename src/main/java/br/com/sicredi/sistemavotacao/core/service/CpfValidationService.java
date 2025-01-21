package br.com.sicredi.sistemavotacao.core.service;

import br.com.sicredi.sistemavotacao.infra.gateway.CpfValidationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CpfValidationService {

    private final CpfValidationRepository cpfValidationRepository;

    public CpfValidationService(CpfValidationRepository cpfValidationRepository) {
        this.cpfValidationRepository = cpfValidationRepository;
    }

    public Mono<Boolean> isEligibleToVote(String cpf) {
        return cpfValidationRepository.isEligibleToVote(cpf);
    }
}
