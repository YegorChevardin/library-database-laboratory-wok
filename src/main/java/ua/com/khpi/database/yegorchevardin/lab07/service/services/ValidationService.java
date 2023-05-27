package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotValidException;

import java.util.Set;

/**
 * Interface for validation
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface ValidationService<T> {
    /**
     * Default validation method realization
     */
    default void validate(Validator validator, T model) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                stringBuilder.append("ERROR:    ")
                        .append(violation.getMessage())
                        .append(System.lineSeparator())
                        .append(";");
            }
            throw new DataNotValidException(stringBuilder.toString());
        }
    }
}
