package br.com.sicredi.sistemavotacao.application.controller;

import br.com.sicredi.sistemavotacao.core.domain.Associado;
import br.com.sicredi.sistemavotacao.core.service.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/associados")
@Tag(name = "Associado", description = "Endpoints para gerenciar os associados")
public class AssociadoController {

    private AssociadoService associadoService;

    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @PostMapping
    @Operation(summary = "Criar novo associado")
    public Mono<Associado> criarNovoAssociado(@RequestBody Associado associado) {
        return associadoService.save(Associado.create(associado.name(), associado.cpf(), associado.statusVoto()));
    }

    @GetMapping("/{associadoId}")
    @Operation(summary = "Retornar associado por id")
    public Mono<Associado> retornarAssociadoPorId(@PathVariable UUID associadoId) {
        return associadoService.findById(associadoId);
    }

}
