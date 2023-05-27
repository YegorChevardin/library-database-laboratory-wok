package ua.com.khpi.database.yegorchevardin.lab07.database.repositories;

import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Author;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Book;

import java.util.Optional;

/**
 * Interface for defining and executing queries from lab03
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface Lab4Repository {
    Table showAuthors();

    Table showBooks();

    Book addBook(Book book);

    Author addAuthor(Author author);
}
