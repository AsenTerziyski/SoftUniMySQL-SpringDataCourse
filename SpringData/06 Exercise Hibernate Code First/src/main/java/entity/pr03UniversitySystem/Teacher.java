package entity.pr03UniversitySystem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends User {

    private BigDecimal salary;
    private String email;
    private Set<Course> courses;

    public Teacher() {
        this.courses = new HashSet<>();
    }


    @Column(name = "salary")
    public BigDecimal getSalary() {
        return salary;
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "teacher")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
