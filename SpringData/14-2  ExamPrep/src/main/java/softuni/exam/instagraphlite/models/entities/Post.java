package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{
    //•	id – integer number, primary identification field, auto increment.
    //•	caption – a char sequence. Cannot be null. Must be at least 21 characters, inclusive.
    //•	user – a User. Cannot be null.
    //•	picture – a Picture. Cannot be null.

    private String caption;
    private User user;
    private Picture picture;

    public Post() {
    }

    @Column(name = "caption", nullable = false)
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

//    @Column(name = "users",nullable = false) => ако сложа тази анотация не тръгва
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @Column(nullable = false) => ако сложа тази анотация не тръгва
    @ManyToOne(optional = false)
    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
