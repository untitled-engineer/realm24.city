package engineer.untitled.realm.entity.propertyParts.type;

import engineer.untitled.realm.entity.propertyParts.Type;

import javax.persistence.*;

// Участок
@Entity(name = "LandPlot")
@Table(name = "properties_building")
public class LandPlot extends Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public LandPlot(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
