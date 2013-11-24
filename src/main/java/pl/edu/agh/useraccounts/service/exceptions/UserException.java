package pl.edu.agh.useraccounts.service.exceptions;

public class UserException extends Exception {
    private int exceptionCode;

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
