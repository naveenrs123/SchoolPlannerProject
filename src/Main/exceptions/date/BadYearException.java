package exceptions.date;

public class BadYearException extends BadDateInputException {

    public BadYearException(String message) {
        super(message);
    }
}
