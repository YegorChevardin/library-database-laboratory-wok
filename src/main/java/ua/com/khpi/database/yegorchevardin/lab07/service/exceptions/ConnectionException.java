package ua.com.khpi.database.yegorchevardin.lab07.service.exceptions;

/**
 * Exception for case if something wrong
 * with database layer
 * @author yegorchevardin
 * @version 0.0.1
 */
public class ConnectionException extends RuntimeException {
    public ConnectionException(String message, Exception e) {
        super(message, e);
    }
}
