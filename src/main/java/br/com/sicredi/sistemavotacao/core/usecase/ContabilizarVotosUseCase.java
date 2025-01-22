package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.domain.ResultadoVotacao;
import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;
import br.com.sicredi.sistemavotacao.core.repository.VotoRepository;
import br.com.sicredi.sistemavotacao.core.service.SqsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ContabilizarVotosUseCase {

    private final static Logger log = LoggerFactory.getLogger(ContabilizarVotosUseCase.class);

    private final VotoRepository votoRepository;
    private final SqsService sqsService;

    public ContabilizarVotosUseCase(VotoRepository votoRepository, SqsService sqsService) {
        this.votoRepository = votoRepository;
        this.sqsService = sqsService;
    }

    public Mono<ResultadoVotacao> execute(UUID pautaId) {
        log.info("Iniciando a contabilização de votos para a pauta={}", pautaId);
        return votoRepository.findByPautaId(pautaId)
                .collectList()
                .map(votos -> {
                    long votosSim = votos.stream().filter(voto -> voto.opcaoVoto().equals(OpcaoVoto.SIM)).count();
                    long votosNao = votos.size() - votosSim;
                    long totalVotos = votos.size();

                    var resultado = new ResultadoVotacao(pautaId, votosSim, votosNao, totalVotos);

                    var sendMessageResponseMono = sqsService.sendVoteResult(resultado);

                    log.info("Resposta de envio para a fila SQS - {}", sendMessageResponseMono);

                    return resultado;
                });
    }
}
