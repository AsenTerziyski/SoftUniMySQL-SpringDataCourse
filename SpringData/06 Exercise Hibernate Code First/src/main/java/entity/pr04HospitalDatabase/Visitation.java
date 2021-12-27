package entity.pr04HospitalDatabase;

import entity.BaseEntity;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "visitations")
public class Visitation  extends BaseEntity {
    private String comment;
    private DateFormat visitDate;
    private Patient patient;

    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Visitation() {

    }

    @Column (name = "comments", columnDefinition = "TEXT")
    public String getComment() {
        return comment;
    }

    @Column (name = "vsitation_date")
    public DateFormat getVisitDate() {
        return visitDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setVisitDate(DateFormat visitDate) {
        this.visitDate = visitDate;
    }
}
