package com.johannbandelow.mtgcardapi.exceptions;

import java.util.function.Supplier;

public class NoCardFoundException extends Exception {
    public NoCardFoundException(String errorMessage) {
        super(errorMessage);
    }

}
