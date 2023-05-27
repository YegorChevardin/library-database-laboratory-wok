package ua.com.khpi.database.yegorchevardin.lab07.program.pages.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.AbstractPage;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.Lab3Service;

import java.util.List;

@Component("task3")
public class Task3Page extends AbstractPage {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Show books which was taken at least once by the readers;
            2 - Show authors books and genres;
            3 - Go back;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3);
    private final MenuOptionResolver menuOptionResolver;

    private final Lab3Service lab3Service;

    @Autowired
    public Task3Page(Gson gson, MenuOptionResolver menuOptionResolver, Lab3Service lab3Service) {
        super(gson);
        this.menuOptionResolver = menuOptionResolver;
        this.lab3Service = lab3Service;
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
                System.out.println(lab3Service.booksWhichWasTakenAtLeastOnceByTheReaders().print());
                break;
            case 2:
                System.out.println(lab3Service.authorsBooksAndGenres().print());
                break;
            case 3:
                return;
        }
        execute();
    }
}
