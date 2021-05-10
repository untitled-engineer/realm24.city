package engineer.untitled.switter.entity.propertyParts.type;

import engineer.untitled.switter.entity.propertyParts.Type;

import javax.persistence.*;

// Коттетж
@Entity(name = "Cottage")
@Table(name = "properties_building")
public class Cottage extends Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Cottage(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
