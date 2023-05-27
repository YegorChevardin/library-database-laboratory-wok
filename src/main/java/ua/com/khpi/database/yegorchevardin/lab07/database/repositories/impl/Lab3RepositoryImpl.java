package ua.com.khpi.database.yegorchevardin.lab07.database.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.exception.DatabaseException;
import ua.com.khpi.database.yegorchevardin.lab07.database.repositories.Lab3Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
@RequiredArgsConstructor
public class Lab3RepositoryImpl implements Lab3Repository {
    private final static String booksWhichWasTakenAtLeastOnceByTheReadersQuery =
            "SELECT DISTINCT books.* FROM books RIGHT JOIN subscriptions ON books.b_id = subscriptions.sb_book";
    private static final String authorsBooksAndGenresQuery =
            "SELECT GROUP_CONCAT(DISTINCT a_name SEPARATOR \", \") AS grouped_authors, authors_books, authors_genres\n" +
                    "FROM (SELECT authors.a_name, GROUP_CONCAT(DISTINCT books.b_name SEPARATOR \", \") AS authors_books,\n" +
                    "GROUP_CONCAT(DISTINCT genres.g_name SEPARATOR \", \") AS authors_genres\n" +
                    "FROM authors\n" +
                    "JOIN m2m_books_authors USING(a_id) JOIN books USING(b_id)\n" +
                    "JOIN m2m_books_genres USING(b_id) JOIN genres USING(g_id)\n" +
                    "GROUP BY authors.a_name) AS ungrouped_result\n" +
                    "GROUP BY authors_books, authors_genres;";

    private  final DataSource dataSource;

    @Override
    public Table booksWhichWasTakenAtLeastOnceByTheReaders() {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        booksWhichWasTakenAtLeastOnceByTheReadersQuery
                )
        ) {
            return Table.read().db(
                    resultSet,
                    "booksWhichWasTakenAtLeastOnceByTheReaders"
            );
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Table authorsBooksAndGenres() {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        authorsBooksAndGenresQuery
                )
        ) {
            return Table.read().db(
                    resultSet,
                    "authorsBooksAndGenresQuery"
            );
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
