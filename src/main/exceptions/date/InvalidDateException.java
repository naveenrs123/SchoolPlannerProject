package exceptions.date;

public class InvalidDateException extends BadDateInputException {

    public InvalidDateException(String message) {
        super(message);
    }
}
