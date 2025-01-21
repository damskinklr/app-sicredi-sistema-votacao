package br.com.sicredi.sistemavotacao.application.controller;

import br.com.sicredi.sistemavotacao.application.dto.PautaDto;
import br.com.sicredi.sistemavotacao.application.dto.VotoDto;
import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.domain.ResultadoVotacao;
import br.com.sicredi.sistemavotacao.core.domain.Voto;
import br.com.sicredi.sistemavotacao.core.usecase.AbrirSessaoUseCase;
import br.com.sicredi.sistemavotacao.core.usecase.CadastrarPautaUseCase;
import br.com.sicredi.sistemavotacao.core.usecase.ContabilizarVotosUseCase;
import br.com.sicredi.sistemavotacao.core.usecase.ReceberVotoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/pautas")
public class PautaController {

    private final CadastrarPautaUseCase cadastrarPautaUseCase;
    private final AbrirSessaoUseCase abrirSessaoUseCase;
    private final ReceberVotoUseCase receberVotoUseCase;
    private final ContabilizarVotosUseCase contabilizarVotosUseCase;

    public PautaController(CadastrarPautaUseCase cadastrarPautaUseCase,
                           AbrirSessaoUseCase abrirSessaoUseCase,
                           ReceberVotoUseCase receberVotoUseCase,
                           ContabilizarVotosUseCase contabilizarVotosUseCase) {
        this.cadastrarPautaUseCase = cadastrarPautaUseCase;
        this.abrirSessaoUseCase = abrirSessaoUseCase;
        this.receberVotoUseCase = receberVotoUseCase;
        this.contabilizarVotosUseCase = contabilizarVotosUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Pauta>> cadastrarPauta(@RequestBody PautaDto pauta) {
        return cadastrarPautaUseCase.execute(pauta.titulo(), pauta.descricao())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/abrir")
    public Mono<ResponseEntity<Pauta>> abrirSessao(@PathVariable UUID id, @RequestParam(defaultValue = "1") long minutos) {
        return abrirSessaoUseCase.execute(id, Duration.ofMinutes(minutos))
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/votos")
    public Mono<ResponseEntity<Voto>> votar(@PathVariable UUID id, @RequestBody VotoDto votoDto) {
        return receberVotoUseCase.execute(id, votoDto.associadoId(), votoDto.voto())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}/resultado")
    public Mono<ResponseEntity<ResultadoVotacao>> contabilizarVotos(@PathVariable UUID id) {
        return contabilizarVotosUseCase.execute(id)
                .map(ResponseEntity::ok);
    }



}
