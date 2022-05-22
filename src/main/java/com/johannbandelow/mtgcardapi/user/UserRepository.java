package com.johannbandelow.mtgcardapi.user;


import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(Long userId);
}
