package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Mapeia esta exceção para o erro 404
public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(String message) {
        super(message);
    }
}