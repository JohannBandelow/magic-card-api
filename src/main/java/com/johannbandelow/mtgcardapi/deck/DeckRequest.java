package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.card.Card;
import com.johannbandelow.mtgcardapi.enums.SortTypeEnum;

import java.util.Set;

public class DeckRequest {
    private Long id;
    private String name;
    private Long userId;
    private Set<Integer> cards;
    private SortTypeEnum sortType;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Integer> getCards() {
        return cards;
    }

    public void setCards(Set<Integer> cards) {
        this.cards = cards;
    }

    public SortTypeEnum getSortType() {
        return sortType;
    }

    public void setSortType(SortTypeEnum sortType) {
        this.sortType = sortType;
    }
}
