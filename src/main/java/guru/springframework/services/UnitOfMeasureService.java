package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * @author john
 * @since 10/02/2024
 */
public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAllUoms();
}
