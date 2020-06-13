package by.ladyka.profile.exceptions;

public class ApplicationException extends RuntimeException {
    protected ApplicationException(String detailMessage) {
        super(detailMessage);
    }
}
