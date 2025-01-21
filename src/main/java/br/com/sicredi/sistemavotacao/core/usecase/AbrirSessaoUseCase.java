package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.repository.PautaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AbrirSessaoUseCase {

    private final PautaRepository pautaRepository;

    public AbrirSessaoUseCase(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Mono<Void> execute(UUID pautaId, Duration duracao) {
        return pautaRepository.findById(pautaId)
                .flatMap(pauta -> {
                    var pautaUpdated = pauta.abrirSessao(true, LocalDateTime.now(), LocalDateTime.now().plus(duracao));
                    return pautaRepository.save(pautaUpdated);
                })
                .then();
    }
}
