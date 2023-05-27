package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Book;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;

@Component
@RequiredArgsConstructor
public class BookObjectInputHandler implements ObjectInputHandler<Book> {
    private final MenuOptionResolver menuOptionResolver;

    @Override
    public Book getObjectFromInput() {
        Book book = new Book();
        System.out.println("Please type name for book");
        book.setName(menuOptionResolver.resolveLine());
        System.out.println("Please type year for book");
        book.setYear(Long.valueOf(menuOptionResolver.resolve()));
        System.out.println("Please type quantity of the books");
        book.setQuantity(Long.valueOf(menuOptionResolver.resolve()));
        return book;
    }
}
