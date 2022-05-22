package com.johannbandelow.mtgcardapi.card;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Integer> {

    @Query("From Card")
    public Optional<List<Card>> findCardsByUser(Long userId);
}
