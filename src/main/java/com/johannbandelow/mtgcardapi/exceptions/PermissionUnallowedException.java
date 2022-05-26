package com.johannbandelow.mtgcardapi.exceptions;

public class PermissionUnallowedException extends Exception {
    public PermissionUnallowedException(String message) {
        super(message);
    }
}
