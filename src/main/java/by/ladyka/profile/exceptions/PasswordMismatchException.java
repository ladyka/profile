package by.ladyka.profile.exceptions;

public class PasswordMismatchException extends ApplicationException {

    public PasswordMismatchException() {
        super("Пароли не совпадают!");
    }
}
