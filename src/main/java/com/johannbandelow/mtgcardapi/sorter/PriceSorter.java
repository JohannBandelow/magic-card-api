package com.johannbandelow.mtgcardapi.sorter;

import com.johannbandelow.mtgcardapi.card.Card;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class PriceSorter implements Sort{
    @Override
    public List<Card> sortCards(List<Card> cards) {
        boolean sorted = false;
        Card temporary;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < cards.size() - 1; i++) {
                if (cards.get(i).getPrice() > cards.get(i+1).getPrice()) {
                    temporary = cards.get(i);
                    cards.set(i, cards.get(i+1));
                    cards.set(i+1, temporary);
                    sorted = false;
                }
            }
        }
        return cards;
    }
}
