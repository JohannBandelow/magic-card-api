package com.johannbandelow.mtgcardapi.sorter;

import com.johannbandelow.mtgcardapi.card.Card;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SorterService {

    public List<Card> sortCards(List<Card> cards, Sort sorter) {
        return sorter.sortCards(cards);
    }

}
