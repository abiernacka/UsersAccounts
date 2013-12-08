package pl.edu.agh.useraccounts.service.exceptions;

/**
 * Wyjątek przy zarządzaniu kontem użytkownika, zawiera kod błędu.
 */
public class UserException extends Exception {
    private int exceptionCode;

    public int getExceptionCode() {
        return exceptionCode;
    }

    public UserException setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
        return this;
    }
}
