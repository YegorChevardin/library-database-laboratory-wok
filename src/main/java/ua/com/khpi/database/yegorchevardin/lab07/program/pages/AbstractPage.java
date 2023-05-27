package ua.com.khpi.database.yegorchevardin.lab07.program.pages;

import com.google.gson.Gson;
import ua.com.khpi.database.yegorchevardin.lab07.program.dto.ExceptionDTO;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataExistsException;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotFoundException;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotValidException;

/**
 * Class for defining default method constructions
 * @author yegorchevardin
 * @version 0.0.1
 */
public abstract class AbstractPage implements Page {
    protected final Gson gson;

    public AbstractPage(Gson gson) {
        this.gson = gson;
    }

    public void proceed() {
        try {
            execute();
        } catch (
                DataNotFoundException
                | DataExistsException
                | DataNotValidException e
        ) {
            System.out.println(
                    gson.toJson(
                            new ExceptionDTO(e.getMessage())
                    )
            );
            proceed();
        }
    }

    protected abstract void execute();
}
