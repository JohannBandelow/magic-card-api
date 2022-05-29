package com.johannbandelow.mtgcardapi.deck;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends CrudRepository<Deck, Integer> {

    @Query(name = "SELECT d FROM Deck d WHERE id = :deckId")
    Optional<Deck> findById(@Param("deckId") Long deckId);

    @Query(name = "SELECT d FROM Deck d")
    Optional<List<Deck>> listAll();
}
