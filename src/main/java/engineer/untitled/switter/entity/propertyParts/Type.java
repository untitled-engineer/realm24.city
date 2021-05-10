package engineer.untitled.switter.entity.propertyParts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "building-type")
@Table(name = "properties_building")
abstract public class Type {

    @Id
    @GeneratedValue
    protected Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
