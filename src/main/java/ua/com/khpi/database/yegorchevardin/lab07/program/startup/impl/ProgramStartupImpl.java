package ua.com.khpi.database.yegorchevardin.lab07.program.startup.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.Page;
import ua.com.khpi.database.yegorchevardin.lab07.program.startup.ProgramStartup;

import java.util.List;
import java.util.Map;

@Component
public class ProgramStartupImpl implements ProgramStartup {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Proceed queries from computer task 3;
            2 - Proceed queries from computer task 4;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5);
    private static final String LOGO =
            """      
                             ___      ___   _______  ______    _______  ______    _______  __   __\s
                            |   |    |   | |  _    ||    _ |  |   _   ||    _ |  |       ||  | |  |
                            |   |    |   | | |_|   ||   | ||  |  |_|  ||   | ||  |_     _||  |_|  |
                            |   |    |   | |       ||   |_||_ |       ||   |_||_   |   |  |       |
                            |   |___ |   | |  _   | |    __  ||       ||    __  |  |   |  |_     _|
                            |       ||   | | |_|   ||   |  | ||   _   ||   |  | |  |   |    |   | \s
                            |_______||___| |_______||___|  |_||__| |__||___|  |_|  |___|    |___| \s           
                    """;
    private final Map<String, Page> pages;
    private final MenuOptionResolver menuOptionResolver;

    @Autowired
    public ProgramStartupImpl(
            MenuOptionResolver menuOptionResolver,
            ApplicationContext applicationContext) {
        this.menuOptionResolver = menuOptionResolver;
        pages = applicationContext.getBeansOfType(Page.class);
    }

    @Override
    public void start() {
        run();
    }

    private void run() {
        System.out.println(LOGO);
        menu();
    }

    private void menu() {
        System.out.println(MENU_ITEMS);
        Integer option = menuOptionResolver.resolve(options);
        Page pageToProceed = doMenuAction(option);
        try {
            pageToProceed.proceed();
        } catch (NullPointerException e) {
            System.out.println("Error in the program occurred!");
            System.exit(0);
        }
        menu();
    }

    private Page doMenuAction(Integer option) {
        Page page = null;
        switch (option) {
            case 0 -> System.exit(0);
            case 1 -> page = pages.get("task3");
            case 2 -> page = pages.get("task4");
        }
        return page;
    }
}
