package guru.springframework.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author john
 * @since 03/01/2024
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "UnitOfMeasure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
