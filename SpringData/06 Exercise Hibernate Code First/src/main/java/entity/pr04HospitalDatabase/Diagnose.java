package entity.pr04HospitalDatabase;

import entity.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
@Inheritance (strategy = InheritanceType.JOINED)
public class Diagnose extends BaseEntity {
    private String name;
    private String diagnoseComment;

    private Patient patient;
    private Set<Medicament> prescribedMedicaments;

    public Diagnose() {
        this.prescribedMedicaments = new HashSet<>();
    }

    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    @ManyToMany
    public Set<Medicament> getPrescribedMedicaments() {
        return prescribedMedicaments;
    }

    public void setPrescribedMedicaments(Set<Medicament> prescribedMedicament) {
        this.prescribedMedicaments = prescribedMedicament;
    }

    @Column(name = "diagnose_name", columnDefinition = "TEXT")
    public String getName() {
        return name;
    }
    @Column(name = "diagnose_comment", columnDefinition = "TEXT")
    public String getDiagnoseComment() {
        return diagnoseComment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiagnoseComment(String diagnoseComment) {
        this.diagnoseComment = diagnoseComment;
    }
}
