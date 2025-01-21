package br.com.sicredi.sistemavotacao.application.controller;

import br.com.sicredi.sistemavotacao.application.dto.PautaDto;
import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.usecase.AbrirSessaoUseCase;
import br.com.sicredi.sistemavotacao.core.usecase.CadastrarPautaUseCase;
import org.springframework.http.ResponseEntity;
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

    public PautaController(CadastrarPautaUseCase cadastrarPautaUseCase,
                           AbrirSessaoUseCase abrirSessaoUseCase) {
        this.cadastrarPautaUseCase = cadastrarPautaUseCase;
        this.abrirSessaoUseCase = abrirSessaoUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Pauta>> cadastrarPauta(@RequestBody PautaDto pauta) {
        return cadastrarPautaUseCase.execute(pauta.titulo(), pauta.descricao())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/abrir")
    public Mono<ResponseEntity<Void>> abrirSessao(@PathVariable UUID id, @RequestParam(defaultValue = "1") long minutos) {
        return abrirSessaoUseCase.execute(id, Duration.ofMinutes(minutos))
                .thenReturn(ResponseEntity.ok().build());
    }



}
