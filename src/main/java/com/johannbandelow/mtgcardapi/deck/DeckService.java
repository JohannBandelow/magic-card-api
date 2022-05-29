package com.johannbandelow.mtgcardapi.deck;

import com.johannbandelow.mtgcardapi.card.Card;
import com.johannbandelow.mtgcardapi.card.CardService;
import com.johannbandelow.mtgcardapi.enums.SortTypeEnum;
import com.johannbandelow.mtgcardapi.exceptions.*;
import com.johannbandelow.mtgcardapi.sorter.AlphabeticalSorter;
import com.johannbandelow.mtgcardapi.sorter.PriceSorter;
import com.johannbandelow.mtgcardapi.sorter.SorterService;
import com.johannbandelow.mtgcardapi.user.User;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
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
            this.placeCards(deck, request);
        }

        return deckRepository.save(deck);
    }

    public Deck getDeckById(Long deckId, SortTypeEnum sortTypeEnum) throws NoDeckFoundException, BadRequestException {
        if (deckId == null) {
            throw new BadRequestException("ID do deck não enviado!");
        }
        Deck deck = deckRepository
                .findById(deckId)
                .orElseThrow(() -> new NoDeckFoundException(deckId));

        if (sortTypeEnum != null && sortTypeEnum.equals(SortTypeEnum.VALUE)) {
            deck.setCards(sorterService.sortCards(deck.getCards(), new PriceSorter()));
        } else {
            deck.setCards(sorterService.sortCards(deck.getCards(), new AlphabeticalSorter()));
        }

        return deck;
    }

    @Transactional
    public Deck editDeck(DeckRequest deckRequest) throws NoUserFoundException, BadRequestException, PermissionUnallowedException, NoDeckFoundException {
        User user = userService.getUserById(deckRequest.getUserId());

        Deck deck = deckRepository
                .findById(deckRequest.getId())
                .orElseThrow(() -> new NoDeckFoundException(deckRequest.getId()));

        if (!user.getId().equals(deck.getUser().getId())) {
            throw new PermissionUnallowedException("Você não tem permissão para editar o deck de outro usuário");
        }

        if (!deckRequest.getName().isBlank()) {
            deck.setName(deckRequest.getName());
        }

        if (!deckRequest.getCards().isEmpty()) {
            deck.setCards(new ArrayList<Card>());
            this.placeCards(deck, deckRequest);
        }

        return deckRepository.save(deck);
    }

    private void placeCards(Deck deck, DeckRequest deckRequest) {
        for (Integer cardId : deckRequest.getCards()) {
            try {
                deck.addCard(cardService.getCardByIdAndUser(Long.valueOf(cardId), deckRequest.getUserId()));
            } catch (NoCardFoundException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public List<Deck> listDecks(Integer deckQuantity, Integer page) throws NoDeckFoundException, BadRequestException {
        if (deckQuantity > 20) {
            throw new BadRequestException("Por questões de performance, não é possível requisitar mais de 20 decks ao mesmo tempo! Use a paginação!");
        }

        return deckRepository.findAllDecks(PageRequest.of(page, deckQuantity)).orElseThrow(NoDeckFoundException::new);
    }
}