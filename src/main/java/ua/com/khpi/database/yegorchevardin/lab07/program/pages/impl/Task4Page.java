package ua.com.khpi.database.yegorchevardin.lab07.program.pages.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Author;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Book;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.AbstractPage;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.Lab4Service;

import java.util.List;

@Component("task4")
public class Task4Page extends AbstractPage {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Show books;
            2 - Show authors;
            3 - Insert Book;
            4 - Insert Author;
            5 - Go back;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5);

    private final MenuOptionResolver menuOptionResolver;
    private final ObjectInputHandler<Book> bookObjectInputHandler;
    private final ObjectInputHandler<Author> authorObjectInputHandler;
    private final Lab4Service lab4Service;

    public Task4Page(
            Gson gson,
            MenuOptionResolver menuOptionResolver,
            ObjectInputHandler<Book> bookObjectInputHandler,
            ObjectInputHandler<Author> authorObjectInputHandler,
            Lab4Service lab4Service
    ) {
        super(gson);
        this.menuOptionResolver = menuOptionResolver;
        this.bookObjectInputHandler = bookObjectInputHandler;
        this.authorObjectInputHandler = authorObjectInputHandler;
        this.lab4Service = lab4Service;
    }

    @Override
    protected void execute() {
        System.out.println(MENU_ITEMS);
        Integer option = menuOptionResolver.resolve(options);
        switch (option) {
            case 0:
                System.exit(0);
                break;
            case 1:
                System.out.println(lab4Service.showBooks().print());
                break;
            case 2:
                System.out.println(lab4Service.showAuthors().print());
                break;
            case 3:
                Book book = bookObjectInputHandler.getObjectFromInput();
                System.out.println(gson.toJson(
                        lab4Service.addBook(book)
                ));
                break;
            case 4:
                Author author = authorObjectInputHandler.getObjectFromInput();
                System.out.println(
                        gson.toJson(
                                lab4Service.addAuthor(author)
                        )
                );
                break;
            case 5:
                return;
        }
        execute();
    }
}
