package com.johannbandelow.mtgcardapi.exceptions;

public class NoDeckFoundException extends Exception {
    public NoDeckFoundException(String message) {
        super(message);
    }

    public NoDeckFoundException() {
        super("Nenhum deck encontrado!");
    }

    public NoDeckFoundException(Long id) {
        super("Nenhum deck encontrado com o ID: " + id);
    }
}
