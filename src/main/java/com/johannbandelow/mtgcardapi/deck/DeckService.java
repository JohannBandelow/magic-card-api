package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.card.Card;
import com.johannbandelow.mtgcardapi.card.CardService;
import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import com.johannbandelow.mtgcardapi.exceptions.NoDeckFoundException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.user.User;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class DeckService {

    Logger logger = LogManager.getLogger(DeckService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private DeckRepository deckRepository;

    @Transactional
    public Deck createDeck(DeckRequest request) throws BadRequestException, NoUserFoundException {
        Deck deck = new Deck();

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Nome do deck não enviado!");
        } else {
            deck.setName(request.getName());
        }

        if (request.getUserId() == null) {
            throw new BadRequestException("ID do usuário não enviado");
        }

        User user = userService.getUserById(request.getUserId());
        if (user == null) {
            throw new NoUserFoundException("Usuário não encontrado, id:" + request.getUserId());
        } else {
            deck.setUser(user);
        }

        if(!request.getCards().isEmpty()) {
            Set<Card> cards = new HashSet<Card>();
            for (Integer cardId : request.getCards()) {
                Optional<Card> card = cardService.getCardByIdAndUser(Long.valueOf(cardId), request.getUserId());
                if (card.isPresent()) {
                    if (deck.getCards() == null) {
                        cards.add(card.get());
                    }
                }
            }
            deck.setCards(cards);
        }

        return deckRepository.save(deck);
    }

    public Deck getDeckById(Long deckId) throws NoDeckFoundException {
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);

        if (optionalDeck.isPresent()) {
            return optionalDeck.get();
        } else {
            throw new NoDeckFoundException("Nenhum deck encontrado, id: " + deckId);
        }
    }

    @Transactional
    public List<Deck> listDecks() {
        return (List<Deck>) deckRepository.findAll();
    }
}
