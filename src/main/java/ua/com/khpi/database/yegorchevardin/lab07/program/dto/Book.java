package ua.com.khpi.database.yegorchevardin.lab07.program.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for books
 * @author yegorchevardin
 * @version 0.0.1
 */
@Data
public class Book {
    private Long id;
    @NotNull(message = "Book name cannot be null")
    @Length(min = 1, max = 150, message = "Book name cannot be greater than 150 and less then 1 character")
    private String name;
    @NotNull(message = "Year of the book cannot be null")
    @Min(value = 0, message = "Year cannot be negative")
    private Long year;
    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;
}
