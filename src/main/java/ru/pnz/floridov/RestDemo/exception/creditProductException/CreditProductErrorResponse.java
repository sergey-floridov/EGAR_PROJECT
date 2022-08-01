package ru.pnz.floridov.RestDemo.exception.creditProductException;

public class CreditProductErrorResponse {
    private String message;

    public CreditProductErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
