package com.johannbandelow.mtgcardapi.card;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Integer> {

    @Query("SELECT c FROM Card WHERE c.user_id = :userId")
    Optional<List<Card>> findCardsByUser(@Param("userId") Long userId);

    @Query(value = "SELECT c FROM Card WHERE c.id = :id")
    Optional<Card> findById(@Param("id") Long cardId);

    @Query(value = "DELETE c FROM Card WHERE c.id = :id")
    void deleteById(Long cardId);
}
