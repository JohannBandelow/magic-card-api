package com.johannbandelow.mtgcardapi.exceptions;

public class UserEmailAlreadyExists extends Exception {
    public UserEmailAlreadyExists(String email) {
        super("Email já cadastrado! email: " + email);
    }
}
