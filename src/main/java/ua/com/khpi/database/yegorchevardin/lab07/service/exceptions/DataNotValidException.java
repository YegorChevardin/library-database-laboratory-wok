package ua.com.khpi.database.yegorchevardin.lab07.service.exceptions;

/**
 * Exception for case in passed parameters was not valid
 * @author yegorchevardin
 * @version 0.0.1
 */
public class DataNotValidException extends RuntimeException {
    public DataNotValidException(String message) {
        super(message);
    }
}
