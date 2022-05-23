package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deck")
public class DeckController {

    @Autowired
    private DeckService deckService;

    Logger logger = LogManager.getLogger(DeckController.class);

    @PostMapping
    public ResponseEntity<?> createDeck(@RequestBody Deck deck) {
        try {
            Deck createdDeck = deckService.createDeck(deck);
            return ResponseEntity.status(HttpStatus.OK).body(createdDeck);
        } catch (BadRequestException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listDecks() {
        List<Deck> decks = deckService.listDecks();

        return ResponseEntity.status(HttpStatus.OK).body(decks);
    }
}
