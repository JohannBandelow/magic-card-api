package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    Logger logger = LogManager.getLogger(CardController.class);

    @GetMapping
    public ResponseEntity<?> getUserCards(@RequestParam Long userId) {
        List<Card> cards = new ArrayList<>();

        try {
            cards = cardService.getUserCards(userId);
        } catch (NoUserFoundException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @PostMapping(path = "/add")
    public Card addCardToUser(@RequestParam Long userId, @RequestBody Card card) {

        return cardService.addCardToUser(userId, card);
    }
}