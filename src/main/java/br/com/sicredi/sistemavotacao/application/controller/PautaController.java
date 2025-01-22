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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pauta", description = "Endpoints para gerenciar as pautas")
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
    @Operation(summary = "Criar nova pauta")
    public Mono<ResponseEntity<Pauta>> cadastrarPauta(@RequestBody PautaDto pauta) {
        return cadastrarPautaUseCase.execute(pauta.titulo(), pauta.descricao())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/abrir")
    @Operation(summary = "Abrir sessão para votação")
    public Mono<ResponseEntity<Pauta>> abrirSessao(@PathVariable UUID id, @RequestParam(defaultValue = "1") long minutos) {
        return abrirSessaoUseCase.execute(id, Duration.ofMinutes(minutos))
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/votos")
    @Operation(summary = "Registrar Votos")
    public Mono<ResponseEntity<Voto>> votar(@PathVariable UUID id, @RequestBody VotoDto votoDto) {
        return receberVotoUseCase.execute(id, votoDto.associadoId(), votoDto.voto())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}/resultado")
    @Operation(summary = "Contabilizar Votos")
    public Mono<ResponseEntity<ResultadoVotacao>> contabilizarVotos(@PathVariable UUID id) {
        return contabilizarVotosUseCase.execute(id)
                .map(ResponseEntity::ok);
    }

}
