package softuni.exam.instagraphlite.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    //•	id – integer number, primary identification field, auto increment.
    //•	path – a char sequence. Cannot be null. The path is unique.
    //•	size – a floating point number. Cannot be null. Must be between 500 and 60000 (both numbers are INCLUSIVE)

    private String path;
    private double size;

    public Picture() {
    }

    @Column(name = "path", nullable = false,unique = true)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "size", nullable = false)
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
