package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MenuOptionResolverImpl implements MenuOptionResolver {
    private final Scanner scanner;

    @Override
    public Integer resolve(List<Integer> options) {
        System.out.println("Type here option you want to proceed:");
        Integer option = scannInteger();
        if (!options.contains(option)) {
            System.out.println("Please, pick up correct variant!");
            option = resolve(options);
        }
        return option;
    }

    @Override
    public Integer resolve() {
        System.out.println("Type number you want to proceed: ");
        return scannInteger();
    }

    @Override
    public String resolveLine() {
        System.out.println("Please, type here string to proceed: ");
        return scanner.nextLine();
    }

    private Integer scannInteger() {
        Integer result;
        try {
            result = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Integer is invalid, try again: ");
            result = scannInteger();
        }
        return result;
    }
}
