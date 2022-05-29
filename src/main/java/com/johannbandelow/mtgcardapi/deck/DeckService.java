package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.card.Card;
import com.johannbandelow.mtgcardapi.card.CardService;
import com.johannbandelow.mtgcardapi.enums.SortTypeEnum;
import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import com.johannbandelow.mtgcardapi.exceptions.NoCardFoundException;
import com.johannbandelow.mtgcardapi.exceptions.NoDeckFoundException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.sorter.AlphabeticalSorter;
import com.johannbandelow.mtgcardapi.sorter.PriceSorter;
import com.johannbandelow.mtgcardapi.sorter.SorterService;
import com.johannbandelow.mtgcardapi.user.User;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SorterService sorterService;

    @Transactional
    public Deck createDeck(DeckRequest request) throws BadRequestException, NoUserFoundException {
        Deck deck = new Deck();

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Nome do deck não enviado!");
        } else {
            deck.setName(request.getName());
        }

        deck.setUser(userService.getUserById(request.getUserId()));

        if (!request.getCards().isEmpty()) {
            for (Integer cardId : request.getCards()) {
                try {
                    deck.addCard(cardService.getCardByIdAndUser(Long.valueOf(cardId), request.getUserId()));
                } catch (NoCardFoundException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        return deckRepository.save(deck);
    }

    public Deck getDeckById(Long deckId, SortTypeEnum sortTypeEnum) throws NoDeckFoundException, BadRequestException {
        if (deckId == null) {
            throw new BadRequestException("ID do deck não enviado!");
        }
        Deck deck = deckRepository
                .findById(deckId)
                .orElseThrow(() -> new NoDeckFoundException("Nenhum deck encontrado com o ID passado! ID: " + deckId));

        if (sortTypeEnum != null && sortTypeEnum.equals(SortTypeEnum.VALUE)) {
            deck.setCards(sorterService.sortCards(deck.getCards(), new PriceSorter()));
        } else {
            deck.setCards(sorterService.sortCards(deck.getCards(), new AlphabeticalSorter()));
        }

        return deck;
    }

    @Transactional
    public Deck editDeck(DeckRequest deckRequest) throws NoUserFoundException, BadRequestException {
        User user = userService.getUserById(deckRequest.getUserId());

        Optional<Deck> deck = deckRepository.findById(deckRequest.getId());
        return deck.get();
    }

    public List<Deck> listDecks() throws NoDeckFoundException {

        return deckRepository.listAll().orElseThrow(() -> new NoDeckFoundException("Nenhum deck encontrado!"));
    }
}