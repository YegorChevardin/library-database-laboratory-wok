package ua.com.khpi.database.yegorchevardin.lab07.database.exception;

/**
 * Exception for case something wrong with database
 * @author yegorchevardin
 * @version 0.0.1
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Exception e) {
        super(message, e);
    }

    public DatabaseException(String message) {
        super(message);
    }
}
