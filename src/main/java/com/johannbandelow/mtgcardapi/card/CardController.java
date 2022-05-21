package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public List<Card> getUserCards(Long userId) {
        List<Card> cards = new ArrayList<>();

        cards = cardService.getUserCards(userId);

        return cards;
    }
}
