package com.johannbandelow.mtgcardapi.exceptions;

public class NoCardFoundException extends Exception{
    public NoCardFoundException(String errorMessage) {
        super(errorMessage);
    }

}
