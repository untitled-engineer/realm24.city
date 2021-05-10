package engineer.untitled.switter.entity;


import javax.persistence.*;
import javax.validation.Valid;
import java.lang.annotation.Target;

@Entity
@Valid
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Category (){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
