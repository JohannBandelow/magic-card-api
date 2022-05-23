package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.card.Card;
import com.johannbandelow.mtgcardapi.card.CardService;
import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.user.User;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        }

        if (request.getUserId() == null) {
            throw new BadRequestException("ID do usuário não enviado");
        }

        User user = userService.getUserById(request.getUserId());
        if (user == null) {
            throw new NoUserFoundException("Usuário não encontrado, id:" + request.getUserId());
        }

        if(!request.getCards().isEmpty()) {
            for (Integer cardId : request.getCards()) {
                Optional<Card> card = cardService.getCardById(Long.valueOf(cardId));
                card.ifPresent(value -> deck.getCards().add(value));
            }
        }

        return deckRepository.save(deck);
    }

    @Transactional
    public List<Deck> listDecks() {
        return (List<Deck>) deckRepository.findAll();
    }
}
