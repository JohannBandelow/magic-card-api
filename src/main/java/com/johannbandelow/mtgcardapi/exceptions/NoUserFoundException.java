package com.johannbandelow.mtgcardapi.exceptions;

public class NoUserFoundException extends Exception {
    public NoUserFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NoUserFoundException() {
        super("Nenhum usuário encontrado!");
    }

    public NoUserFoundException(Long id) {
        super("Nenhum usuário encontrado com o ID: " + id);
    }
}
