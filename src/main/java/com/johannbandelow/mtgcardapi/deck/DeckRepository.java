package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.user.User;
import org.springframework.data.repository.CrudRepository;

public interface DeckRepository extends CrudRepository<User, Integer> {
}
