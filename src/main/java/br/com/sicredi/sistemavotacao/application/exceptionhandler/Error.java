package br.com.sicredi.sistemavotacao.application.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Error(
        String code,
        String message,
        String cause
) {
}
