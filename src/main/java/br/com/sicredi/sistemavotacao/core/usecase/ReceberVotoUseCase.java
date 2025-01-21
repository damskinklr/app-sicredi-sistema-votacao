package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.domain.Voto;
import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;
import br.com.sicredi.sistemavotacao.core.repository.PautaRepository;
import br.com.sicredi.sistemavotacao.core.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReceberVotoUseCase {

    private static final Logger log = LoggerFactory.getLogger(ReceberVotoUseCase.class);

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;

    public ReceberVotoUseCase(PautaRepository pautaRepository,
                              VotoRepository votoRepository) {
        this.pautaRepository = pautaRepository;
        this.votoRepository = votoRepository;
    }

    public Mono<Voto> execute(UUID pautaId, String associadoId, OpcaoVoto voto) {
        return pautaRepository.findById(pautaId)
                .flatMap(pauta -> {
                    if (!pauta.aberta() || LocalDateTime.now().isAfter(pauta.fechamento())) {
                        pautaRepository.save(pauta.fecharSessao());
                        return Mono.error(new IllegalStateException("Sessão encerrada"));
                    }

                    return votoRepository.existsByPautaIdAndAssociadoId(pautaId, associadoId)
                            .flatMap(exists -> {
                                if (Boolean.TRUE.equals(exists)) {
                                    return Mono.error(new IllegalStateException("Associado já votou"));
                                }

                                var novoVoto = Voto.create(pautaId, associadoId, voto);
                                return votoRepository.save(novoVoto);
                            }).onErrorResume(e -> {
                                log.error("Erro no processo de votação para pautaId={} e associadoId={}: {}", pautaId, associadoId, e.getMessage());
                                return Mono.error(e); // Propaga o erro com contexto adicional
                            });
                });
    }
}
