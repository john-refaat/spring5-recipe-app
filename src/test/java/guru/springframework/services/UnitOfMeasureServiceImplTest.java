package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 10/02/2024
 */
@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void findAllUoms() {
        //Given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        //When
        Mockito.when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.findAllUoms();

        //Then
        assertNotNull(unitOfMeasureCommands);
        assertEquals(2, unitOfMeasureCommands.size());
        Mockito.verify(unitOfMeasureRepository, Mockito.times(1)).findAll();
    }


}