package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.repositories.Lab4Repository;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Author;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Book;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.Lab4Service;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.ValidationService;

@Service
@RequiredArgsConstructor
public class Lab4ServiceImpl implements Lab4Service {
    private final Validator validator;
    private final Lab4Repository lab4Repository;
    private final ValidationService<Book> bookValidationService;
    private final ValidationService<Author> authorValidationService;

    @Override
    public Table showAuthors() {
        return lab4Repository.showAuthors();
    }

    @Override
    public Table showBooks() {
        return lab4Repository.showBooks();
    }

    @Override
    public Book addBook(Book book) {
        bookValidationService.validate(validator, book);
        return lab4Repository.addBook(book);
    }

    @Override
    public Author addAuthor(Author author) {
        authorValidationService.validate(validator, author);
        return lab4Repository.addAuthor(author);
    }
}
