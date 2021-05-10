package engineer.untitled.switter.entity;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Valid
@Table(name = "property_owners")
public class PropertyOwner
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public PropertyOwner(){

    };

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
