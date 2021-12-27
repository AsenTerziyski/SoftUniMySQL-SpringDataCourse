package entity.pr03UniversitySystem;

import entity.BaseEntity;
import entity.pr03UniversitySystem.Student;
import entity.pr03UniversitySystem.Teacher;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer credits;

    private Set<Student> students;
    private Teacher teacher;

    public Course() {
        this.students = new HashSet<>();
    }

    //edin uchitel mozhe da vodi mnogo kursove = > Many courses to One Teacher:
    @ManyToOne
    public Teacher getTeacher() {
        return teacher;
    }

    //edin kurs ima mnogo studenti, a edin student mozhe da se zapisal za mnogo kursove => many to many:
    @ManyToMany
    public Set<Student> getStudents() {
        return students;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return endDate;
    }

    @Column(name = "credits")
    public Integer getCredits() {
        return credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

}
