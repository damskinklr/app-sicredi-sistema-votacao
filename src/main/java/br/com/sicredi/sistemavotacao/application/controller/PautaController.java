package br.com.sicredi.sistemavotacao.application.controller;

import br.com.sicredi.sistemavotacao.application.dto.PautaDto;
import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.usecase.CadastrarPautaUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/pautas")
public class PautaController {

    private final CadastrarPautaUseCase cadastrarPautaUseCase;

    public PautaController(CadastrarPautaUseCase cadastrarPautaUseCase) {
        this.cadastrarPautaUseCase = cadastrarPautaUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Pauta>> cadastrarPauta(@RequestBody PautaDto pauta) {
        return cadastrarPautaUseCase.execute(pauta.titulo(), pauta.descricao())
                .map(ResponseEntity::ok);
    }
}
