package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.deck.Deck;
import com.johannbandelow.mtgcardapi.enums.LanguageEnum;
import com.johannbandelow.mtgcardapi.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CARDS")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LanguageEnum language;

    private Boolean isFoil;

    private Double price;

    private String expansionPack;

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

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }

    public Boolean getIsFoil() {
        return this.isFoil;
    }

    public void setIsFoil(Boolean isFoil) {
        this.isFoil = isFoil;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getExpansionPack() {
        return expansionPack;
    }

    public void setExpansionPack(String expansionPack) {
        this.expansionPack = expansionPack;
    }
}
