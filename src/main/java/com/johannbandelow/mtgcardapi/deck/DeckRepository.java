package com.johannbandelow.mtgcardapi.deck;

import org.springframework.data.repository.CrudRepository;

public interface DeckRepository extends CrudRepository<Deck, Integer> {
}
