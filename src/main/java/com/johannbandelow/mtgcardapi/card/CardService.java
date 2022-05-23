package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.enums.LanguageEnum;
import com.johannbandelow.mtgcardapi.exceptions.NoCardFoundException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.user.User;
import com.johannbandelow.mtgcardapi.user.UserRepository;
import com.johannbandelow.mtgcardapi.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    Logger logger = LogManager.getLogger(CardService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public List<Card> getUserCards(Long userId) throws NoUserFoundException {
        User user = userService.getUserById(userId);

        if (user == null) {
            throw new NoUserFoundException("Usuário não encontrado, ID: " + userId);
        }

        return (List<Card>) cardRepository.findAll();
    }

    @Transactional
    public Card addCardToUser(Long userId, Card card) {
        try {
            User user = userService.getUserById(userId);

            if (user == null) {
                throw new NoUserFoundException("Usuário não encontrado, ID: " + userId);
            }

            card.setUser(user);
            card = this.validateCard(card);

            return cardRepository.save(card);
        } catch (Exception e) {
              logger.error(e.getMessage(), e);
        }

        return null;
    }

    private Card validateCard(Card card) throws Exception {
        if (card == null) { throw new Exception();}

        if (card.getUser() == null)  { throw new Exception(); }

        if (card.getExpansionPack() == null || card.getExpansionPack().isEmpty()) { throw new Exception(); }

        if (card.getName() == null || card.getName().isEmpty()) { throw new Exception(); }

        if (card.getLanguage() == null) {
            card.setLanguage(LanguageEnum.PORTUGUESE);
        }

        if (card.getIsFoil() == null) {
            card.setIsFoil(false);
        }

        return card;
    }

    @Transactional
    public Card removeCard(Long cardId) throws NoCardFoundException {
        Optional<Card> card = cardRepository.findById(cardId);

        if(card.isEmpty()) {
            throw new NoCardFoundException("Carta não encontrada, id:" + cardId);
        }

        cardRepository.deleteById(cardId);

        return card.get();
    }
}
