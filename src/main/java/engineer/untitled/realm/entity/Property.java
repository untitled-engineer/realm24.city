package engineer.untitled.realm.entity;

import engineer.untitled.realm.entity.propertyParts.Address;
import engineer.untitled.realm.entity.propertyParts.Building;
import engineer.untitled.realm.entity.propertyParts.Photo;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Valid
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private int cadastralNumber;

    @OneToOne
    private PropertyOwner owner;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Photo> layout;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Photo> photos;

    @OneToOne(fetch = FetchType.EAGER)
    private Building building;

    @NotBlank
    @OneToOne
    private Address address;

    public Property(){

    }

    public Property(Address address){
        this.address = address;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}
