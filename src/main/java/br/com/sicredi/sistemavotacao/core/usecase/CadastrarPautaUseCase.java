package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.repository.PautaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CadastrarPautaUseCase {

    private final PautaRepository pautaRepository;

    public CadastrarPautaUseCase(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Mono<Pauta> execute(String titulo, String descricao) {
        var pauta = Pauta.create(titulo, descricao);

        return pautaRepository.save(pauta);
    }
}
