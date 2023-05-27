package ua.com.khpi.database.yegorchevardin.lab07.program.handlers;

import java.util.Optional;

/**
 * Interface for defining methods for input objects
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface ObjectInputHandler<T> {
    /**
     * Method for getting object from input
     */
    T getObjectFromInput();
}
