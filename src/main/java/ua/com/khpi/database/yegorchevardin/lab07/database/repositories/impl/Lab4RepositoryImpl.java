package ua.com.khpi.database.yegorchevardin.lab07.database.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.exception.DatabaseException;
import ua.com.khpi.database.yegorchevardin.lab07.database.repositories.Lab4Repository;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Author;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.Book;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class Lab4RepositoryImpl implements Lab4Repository {
    private static final String showAuthors =
            "SELECT * FROM authors";
    private static final String showBooks =
            "SELECT * FROM books";
    private static final String addBook =
            "INSERT INTO books(b_name, b_year, b_quantity) VALUES (?, ?, ?)";
    private static final String addAuthor =
            "INSERT INTO authors(a_name) VALUES (?)";

    private  final DataSource dataSource;

    @Override
    public Table showAuthors() {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(showAuthors)
        ) {
            return Table.read().db(resultSet, "authors");
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Table showBooks() {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(showBooks)
        ) {
            return Table.read().db(resultSet, "authors");
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Book addBook(Book book) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        addBook, Statement.RETURN_GENERATED_KEYS)
        ) {
            int count = 0;
            statement.setString(++count, book.getName());
            statement.setLong(++count, book.getYear());
            statement.setLong(++count, book.getQuantity());

            int insertedRows = statement.executeUpdate();
            if (insertedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    book.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return book;
    }

    @Override
    public Author addAuthor(Author author) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        addAuthor, Statement.RETURN_GENERATED_KEYS)
        ) {
            int count = 0;
            statement.setString(++count, author.getName());

            int insertedRows = statement.executeUpdate();
            if (insertedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    author.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return author;
    }
}
