package com.johannbandelow.mtgcardapi.deck;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends CrudRepository<Deck, Integer> {

    @Query(value = "SELECT d FROM Deck d WHERE id = :deckId")
    Optional<Deck> findById(@Param("deckId") Long deckId);

    @Query(value = "SELECT d FROM Deck d WHERE name IS NOT NULL")
    Optional<List<Deck>> findAllDecks();
}
