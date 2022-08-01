package ru.pnz.floridov.RestDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnz.floridov.RestDemo.exception.cardException.CardNotFoundException;
import ru.pnz.floridov.RestDemo.model.Card;
import ru.pnz.floridov.RestDemo.model.DebetAccount;
import ru.pnz.floridov.RestDemo.repository.CardRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CardService {

    CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public List<Card> findAll() {
        return cardRepository.findAll();
    }


    public List<Card> findWithPagination(Integer page, Integer cardPerPage) {
        return cardRepository.findAll(PageRequest.of(page, cardPerPage)).getContent();
    }


    public Card findOne(Long id) {
        Optional<Card> foundCard = cardRepository.findById(id);
        return foundCard.orElseThrow(CardNotFoundException::new);
    }


    @Transactional
    public void save(Card card) {
        cardRepository.save(card);
    }


    @Transactional
    public void update(Long id, Card updatedCard) {
        updatedCard.setId(id);
        cardRepository.save(updatedCard);
    }

    @Transactional
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }


    public DebetAccount getDebetAccount(Long id) {

        return cardRepository.findById(id).map(Card::getDebetAccount).orElse(null);
    }
}