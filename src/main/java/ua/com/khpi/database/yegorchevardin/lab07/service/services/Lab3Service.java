package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import tech.tablesaw.api.Table;

/**
 * Service for displaying lab 3
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface Lab3Service {
    /**
     * Method for getting books that was taken by the readers
     */
    Table booksWhichWasTakenAtLeastOnceByTheReaders();

    /**
     * Methods for getting all authors
     * with all books ang genres that they have written
     */
    Table authorsBooksAndGenres();

}
