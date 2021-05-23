package engineer.untitled.realm.entity.propertyParts.type;

import engineer.untitled.realm.entity.propertyParts.Type;

import javax.persistence.*;

// Коммерческая недвижимость
@Entity(name = "Commercial")
@Table(name = "properties_building")
public class Commercial extends Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Commercial(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
