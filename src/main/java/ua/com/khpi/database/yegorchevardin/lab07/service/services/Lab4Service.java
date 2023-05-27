package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Author;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Book;

/**
 * Interface for defining and executing queries from lab03
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface Lab4Service {
    Table showAuthors();

    Table showBooks();

    Book addBook(Book book);

    Author addAuthor(Author author);
}
