package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.exceptions.NoCardFoundException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    Logger logger = LogManager.getLogger(CardController.class);

    @GetMapping
    public ResponseEntity<?> getUserCards(@RequestParam Long userId) {
        Optional<List<Card>> cards;

        try {
            cards = cardService.getUserCards(userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        if (cards.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cards.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Nenhuma carta encontrada!");
    }

    @PostMapping(path = "/add")
    public Card addCardToUser(@RequestParam Long userId, @RequestBody Card card) {

        return cardService.addCardToUser(userId, card);
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<?> removeCard(@RequestParam Long cardId, @RequestParam Long userId) {
        Card card = null;

        try {
            card = cardService.removeCard(cardId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getCardById(@RequestParam Long cardId) {
        Optional<Card> card;

        try {
            card = cardService.getCardById(cardId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        if (card.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(card.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Nenhuma carta encontrada!");
    }
}
