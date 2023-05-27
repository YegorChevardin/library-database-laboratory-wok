package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Author;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;

@Component
@RequiredArgsConstructor
public class AuthorObjectInputHandler implements ObjectInputHandler<Author> {
    private final MenuOptionResolver menuOptionResolver;

    @Override
    public Author getObjectFromInput() {
        Author author = new Author();
        System.out.println("Please type name for author: ");
        String name = menuOptionResolver.resolveLine();
        author.setName(name);
        return author;
    }
}
