package ua.com.khpi.database.yegorchevardin.lab07.service.exceptions;

/**
 * Exception for case if data was not found in the database
 * @author yegorchevardin
 * @version 0.0.1
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
