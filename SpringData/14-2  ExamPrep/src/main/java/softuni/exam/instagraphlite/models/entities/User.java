package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    //•	username – a char sequence. Cannot be null. The username is unique. Must be between 2 and 18 (both numbers are INCLUSIVE)
    //•	password – a char sequence. Cannot be null. Must be at least 4 characters long, inclusive.
    //•	profilePicture – a Picture. Cannot be null.

    private String username;
    private String password;
    private Picture profilePicture;

    private Set<Post> posts;

    public User() {
        this.posts = new HashSet<>();
    }


    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER)
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public User(Set<Post> posts) {
        this.posts = new HashSet<>();
    }


    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne(optional = false)
    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }
}
