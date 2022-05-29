package com.johannbandelow.mtgcardapi.exceptions;

import java.util.function.Supplier;

public class NoCardFoundException extends Exception {
    public NoCardFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NoCardFoundException() {
        super("Nenhuma carta encontrada!");
    }

    public NoCardFoundException(Long id) {
        super("Nenhuma carta encontrada com o ID: " + id);
    }

}
