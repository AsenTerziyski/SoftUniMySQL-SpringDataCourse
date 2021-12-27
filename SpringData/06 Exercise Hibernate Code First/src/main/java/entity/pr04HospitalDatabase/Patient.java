package entity.pr04HospitalDatabase;


import com.mysql.cj.jdbc.Blob;
import entity.BaseEntity;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
@Inheritance(strategy = InheritanceType.JOINED)
public class Patient extends BaseEntity {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private DateFormat dateOfBirth;
    private String picture;
    private boolean hasMedicalInsurance;

    private Set<Diagnose> diagnoses;
    private Set<Visitation> visitations;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Patient() {
        this.diagnoses = new HashSet<>();
        this.visitations = new HashSet<>();
    }

    @Column
    public String getFirstName() {
        return firstName;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    @Column
    public String getAddress() {
        return address;
    }

    @Column
    public String getEmail() {
        return email;
    }

    @Column
    public DateFormat getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(name = "jpg", columnDefinition = "BLOB")
    public String getPicture() {
        return picture;
    }

    @Column
    public boolean isHasMedicalInsurance() {
        return hasMedicalInsurance;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(DateFormat dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setHasMedicalInsurance(boolean hasMedicalInsurance) {
        this.hasMedicalInsurance = hasMedicalInsurance;
    }
}
