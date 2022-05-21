package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private UserRepository userRepository;

    public List<Card> getUserCards(Long userId) {
        return null;
    }

}
