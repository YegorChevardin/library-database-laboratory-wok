package ua.com.khpi.database.yegorchevardin.lab07.service.exceptions;

/**
 * Excpetion for case if data already exists in the database
 * @author yegorchevardin
 * @version 0.0.1
 */
public class DataExistsException extends RuntimeException {
    public DataExistsException(String message) {
        super(message);
    }
}
