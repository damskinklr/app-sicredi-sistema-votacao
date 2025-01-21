package br.com.sicredi.sistemavotacao.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class PautaMock {

    public static Pauta createMock() {
        return new Pauta(UUID.randomUUID(), "Titulo", "Descricao", true, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
    }
}
