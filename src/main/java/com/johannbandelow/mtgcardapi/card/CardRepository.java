package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.user.User;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<User, Integer> {
}
