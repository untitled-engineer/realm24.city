package engineer.untitled.realm.entity.propertyParts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Map(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
