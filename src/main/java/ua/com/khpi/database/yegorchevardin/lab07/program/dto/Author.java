package ua.com.khpi.database.yegorchevardin.lab07.program.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for author
 * @author yegorchevardin
 * @version 0.0.1
 */
@Data
public class Author {
    private Long id;
    @NotNull(message = "Author name cannot be null")
    @Length(min = 3, max = 150, message = "Author name should range between 3 and 150 characters")
    private String name;
}
