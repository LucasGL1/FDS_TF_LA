package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Mapeia esta exceção para o erro 400
public class OperacaoInvalidaException extends RuntimeException {
    public OperacaoInvalidaException(String message) {
        super(message);
    }
}