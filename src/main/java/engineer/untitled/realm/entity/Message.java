package engineer.untitled.realm.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Entity
@Valid
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill message")
    @Length(max = 2024, message = "Message too long")
    private String text;

    @Length(max = 255, message = "Message too long (more than 255)")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "author_id")
    private User author;

    @NotBlank(message = "Please set ownership")
    private boolean authorHasOwnedProperty;

    private String filename;

    public Message (){

    }

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAuthorName(){
        return author == null ?  "<none>" : author.getUsername();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
