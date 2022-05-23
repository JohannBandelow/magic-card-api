package com.johannbandelow.mtgcardapi.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
