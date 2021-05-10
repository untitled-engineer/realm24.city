package engineer.untitled.switter.entity.propertyParts.type;

import engineer.untitled.switter.entity.propertyParts.Type;

import javax.persistence.*;

// Дом, Котетж, Таунхаус и прочая хрень
@Entity(name = "House")
@Table(name = "properties_building")
public class House extends Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public House(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
