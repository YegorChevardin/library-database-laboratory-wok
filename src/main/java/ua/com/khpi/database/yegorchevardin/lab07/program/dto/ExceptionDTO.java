package ua.com.khpi.database.yegorchevardin.lab07.program.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class for displaying exceptions that are going from the service
 * @author yegorchevardin
 * @version 0.0.1
 */
@AllArgsConstructor
public class ExceptionDTO {
    @Getter
    @Setter
    private String message;
}
