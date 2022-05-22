package com.johannbandelow.mtgcardapi.exceptions;

public class NoUserFoundException extends Exception {
    public NoUserFoundException(String errorMessage) {
        super(errorMessage);
    }
}
