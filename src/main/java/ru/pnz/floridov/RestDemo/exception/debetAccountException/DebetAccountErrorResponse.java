package ru.pnz.floridov.RestDemo.exception.debetAccountException;

public class DebetAccountErrorResponse {
    private String message;

    public DebetAccountErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
