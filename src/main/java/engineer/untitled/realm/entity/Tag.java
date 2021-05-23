package engineer.untitled.realm.entity;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Valid
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Tag(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
