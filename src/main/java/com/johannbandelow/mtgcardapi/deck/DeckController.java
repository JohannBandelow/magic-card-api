package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import com.johannbandelow.mtgcardapi.exceptions.NoDeckFoundException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
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

    @PostMapping(path = "/create")
    public ResponseEntity<?> createDeck(@RequestBody DeckRequest request) {
        try {
            Deck createdDeck = deckService.createDeck(request);
            return ResponseEntity.status(HttpStatus.OK).body(createdDeck);
        } catch (BadRequestException | NoUserFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> listDecks() {
        List<Deck> decks = deckService.listDecks();

        return ResponseEntity.status(HttpStatus.OK).body(decks);
    }

    @GetMapping
    public ResponseEntity<?> getDeckById(@RequestParam Long deckId) {
        try {
            Deck deck = deckService.getDeckById(deckId);

            return ResponseEntity.status(HttpStatus.OK).body(deck);
        } catch (NoDeckFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
