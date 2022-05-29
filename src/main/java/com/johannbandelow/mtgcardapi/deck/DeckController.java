package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.enums.SortTypeEnum;
import com.johannbandelow.mtgcardapi.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(createdDeck);
        } catch (BadRequestException | NoUserFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> listDecks() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(deckService.listDecks());
        } catch (NoDeckFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/{deckId}")
    public ResponseEntity<?> getDeckById(@PathVariable Long deckId, @RequestParam SortTypeEnum sortType) {
        try {
            Deck deck = deckService.getDeckById(deckId, sortType);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(deck);
        } catch (NoDeckFoundException | BadRequestException e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editDeck(@RequestBody DeckRequest request) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(deckService.editDeck(request));
        } catch (BadRequestException | NoUserFoundException | NoDeckFoundException | PermissionUnallowedException e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
