package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public List<Card> getUserCards(@RequestParam Long userId) {
        List<Card> cards = new ArrayList<>();

        cards = cardService.getUserCards(userId);

        return cards;
    }

    @PostMapping(path = "/add")
    public Card addCardToUser(@RequestParam Long userId, @RequestBody Card card) {

        return cardService.addCardToUser(userId, card);
    }
}
