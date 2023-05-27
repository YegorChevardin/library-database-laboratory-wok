package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.repositories.Lab3Repository;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.Lab3Service;

@Service
@RequiredArgsConstructor
public class Lab3ServiceImpl implements Lab3Service {
    private final Lab3Repository lab3Repository;

    @Override
    public Table booksWhichWasTakenAtLeastOnceByTheReaders() {
        return lab3Repository.booksWhichWasTakenAtLeastOnceByTheReaders();
    }

    @Override
    public Table authorsBooksAndGenres() {
        return lab3Repository.authorsBooksAndGenres();
    }
}
