package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.domain.ResultadoVotacao;
import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;
import br.com.sicredi.sistemavotacao.core.repository.VotoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ContabilizarVotosUseCase {

    private final VotoRepository votoRepository;

    public ContabilizarVotosUseCase(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    public Mono<ResultadoVotacao> execute(UUID pautaId) {
        return votoRepository.findByPautaId(pautaId)
                .collectList()
                .map(votos -> {
                    long votosSim = votos.stream().filter(voto -> voto.opcaoVoto().equals(OpcaoVoto.SIM)).count();
                    long votosNao = votos.size() - votosSim;
                    long totalVotos = votos.size();
                    return new ResultadoVotacao(pautaId, votosSim, votosNao, totalVotos);
                });
    }
}
