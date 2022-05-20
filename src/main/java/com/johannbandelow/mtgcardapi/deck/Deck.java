package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.user.User;

public class Deck {
    private Long id;
    private String name;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
