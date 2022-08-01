package ru.pnz.floridov.RestDemo.exception.clientException;

public class ClientNotCreatedException extends RuntimeException{
    public ClientNotCreatedException(String message){
        super(message);
    }
}
