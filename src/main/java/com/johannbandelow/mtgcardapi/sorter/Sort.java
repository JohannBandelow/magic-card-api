package com.johannbandelow.mtgcardapi.sorter;

import com.johannbandelow.mtgcardapi.card.Card;

import java.util.List;

public interface Sort {

    List<Card> sortCards(List<Card> cards);

}
