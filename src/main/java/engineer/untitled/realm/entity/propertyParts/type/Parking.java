package engineer.untitled.realm.entity.propertyParts.type;

import engineer.untitled.realm.entity.propertyParts.Type;

import javax.persistence.*;

// Дом, Котетж, Таунхаус и прочая хрень
@Entity(name = "Parking")
@Table(name = "properties_building")
public class Parking extends Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Parking(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
