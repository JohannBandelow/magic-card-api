package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DeckService {

    Logger logger = LogManager.getLogger(DeckService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DeckRepository deckRepository;

    @Transactional
    public Deck createDeck(Deck deck) throws BadRequestException {

        if (deck.getName() == null || deck.getName().isBlank()) {
            throw new BadRequestException("Nome do deck não enviado!");
        }

        return deckRepository.save(deck);
    }

    @Transactional
    public List<Deck> listDecks() {
        return (List<Deck>) deckRepository.findAll();
    }
}
