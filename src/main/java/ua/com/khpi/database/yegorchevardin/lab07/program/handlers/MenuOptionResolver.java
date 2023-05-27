package ua.com.khpi.database.yegorchevardin.lab07.program.handlers;

import java.util.List;

/**
 * Interface for getting correct menu value
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface MenuOptionResolver {
    /**
     * Method for resolving option of some range
     */
    Integer resolve(List<Integer> options);

    /**
     * Method for resolving any range
     */
    Integer resolve();

    /**
     * Gets string from input
     */
    String resolveLine();
}
