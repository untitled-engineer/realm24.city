package engineer.untitled.switter.entity.propertyParts;

import engineer.untitled.switter.entity.Developer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Тип объекта недвижемости
    @NotBlank
    @OneToOne(fetch = FetchType.EAGER)
    private Type type;

    // Застройщик

    @OneToOne
    private Developer developer;

    // -- О здании

    // сдан в эксплуатацию
    private boolean inExploitation;

    // if

    // Срок сдачи
    private Date Deadline;


    // подземная парковка
    private boolean undergroundParking;

    public Building(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
