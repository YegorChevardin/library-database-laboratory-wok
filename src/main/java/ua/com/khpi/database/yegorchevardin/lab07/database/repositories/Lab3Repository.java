package ua.com.khpi.database.yegorchevardin.lab07.database.repositories;

import tech.tablesaw.api.Table;

/**
 * Interface for defining and executing queries from lab03
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface Lab3Repository {
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
