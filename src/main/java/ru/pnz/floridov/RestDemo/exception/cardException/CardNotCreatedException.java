package ru.pnz.floridov.RestDemo.exception.cardException;

public class CardNotCreatedException extends RuntimeException {
    public CardNotCreatedException(String msg) {
        super(msg);
    }
}
