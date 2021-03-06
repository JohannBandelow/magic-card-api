package com.johannbandelow.mtgcardapi.card;

import com.johannbandelow.mtgcardapi.enums.LanguageEnum;
import com.johannbandelow.mtgcardapi.exceptions.BadRequestException;
import com.johannbandelow.mtgcardapi.exceptions.NoCardFoundException;
import com.johannbandelow.mtgcardapi.exceptions.NoUserFoundException;
import com.johannbandelow.mtgcardapi.exceptions.PermissionUnallowedException;
import com.johannbandelow.mtgcardapi.user.User;
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
    public List<Card> getUserCards(Long userId) throws NoUserFoundException, BadRequestException, NoCardFoundException {
        User user = userService.getUserById(userId);

        return cardRepository
                .findCardsByUser(userId)
                .orElseThrow(() -> new NoCardFoundException("Nenhuma carta encontrada para o usuário: " + userId));
    }

    @Transactional
    public Card getCardById(Long cardId) throws NoCardFoundException {

        return cardRepository
                .findById(cardId)
                .orElseThrow(() -> new NoCardFoundException(cardId));
    }

    @Transactional
    public Card addCardToUser(Long userId, Card card) throws NoUserFoundException, BadRequestException {
        User user = userService.getUserById(userId);

        card.setUser(user);
        card = this.processCard(card);

        return cardRepository.save(card);
    }

    private Card processCard(Card card) throws BadRequestException {
        if (card == null) {
            throw new BadRequestException("Carta não enviada!");
        }

        if (card.getUser() == null) {
            throw new BadRequestException("Usuário não encontrado!");
        }

        if (card.getExpansionPack() == null || card.getExpansionPack().isEmpty()) {
            throw new BadRequestException("ExpansionPack não enviado!");
        }

        if (card.getName() == null || card.getName().isEmpty()) {
            throw new BadRequestException("Nome da carta não enviada!");
        }

        if (card.getLanguage() == null) {
            card.setLanguage(LanguageEnum.PORTUGUESE);
        }

        if (card.getIsFoil() == null) {
            card.setIsFoil(false);
        }

        return card;
    }

    @Transactional
    public Card removeCard(Long cardId, Long userId) throws NoCardFoundException, PermissionUnallowedException {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new NoCardFoundException(cardId));

        if (!card.getUser().getId().equals(userId)) {
            throw new PermissionUnallowedException("Você não pode remover a carta de outro jogador!");
        }

        cardRepository.deleteById(cardId);
        return card;
    }

    public Card getCardByIdAndUser(Long cardId, Long userId) throws NoCardFoundException {
        return cardRepository
                .findByIdAndUser(cardId, userId)
                .orElseThrow(() -> new NoCardFoundException("Nenhuma carta encontrada para o userId:" + userId + "e com o ID: " + cardId));
    }
}
