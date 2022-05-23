package com.johannbandelow.mtgcardapi.card;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Integer> {

    @Query(value = "SELECT c FROM Card c WHERE c.user = :userId")
    Optional<List<Card>> findCardsByUser(@Param("userId") Long userId);

    void deleteById(Long cardId);

    Optional<Card> findById(Long cardId);
}
