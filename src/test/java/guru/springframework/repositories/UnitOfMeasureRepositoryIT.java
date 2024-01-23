package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 23/01/2024
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByNameTeaspoon() {
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByName("Teaspoon");
        assertTrue(teaspoon.isPresent());
        assertEquals("Teaspoon", teaspoon.get().getName());
    }

    @Test
    void findByNameCup() {
        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByName("Cup");
        assertTrue(cup.isPresent());
        assertEquals("Cup", cup.get().getName());
    }
}