package engineer.untitled.switter.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Valid
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill message")
    @Length(max = 2024, message = "Message too long")
    private String text;

    @Length(max = 255, message = "Message too long (more than 255)")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags;

    @ManyToOne
    @NotBlank
    private User author;

    // the author is the owner of the object
    @NotBlank(message = "Please set ownership")
    private boolean authorIsOwnerOfProperty;

    @OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "ad")
    private Property property;

    @OneToOne
    private Category category;

    public Ad (){

    }

    public Ad(String text, Set<Tag> tags, User author) {
        this.text = text;
        this.tags = tags;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getAuthorName(){
        return author.getUsername();
    }


}
