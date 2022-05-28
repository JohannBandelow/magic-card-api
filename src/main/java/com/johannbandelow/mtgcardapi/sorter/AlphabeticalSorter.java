package com.johannbandelow.mtgcardapi.sorter;

import com.johannbandelow.mtgcardapi.card.Card;

import java.util.Collections;
import java.util.List;

public class AlphabeticalSorter implements Sort{
    @Override
    public List<Card> sortCards(List<Card> cards) {
        Collections.sort(cards);
        return cards;
    }
}
