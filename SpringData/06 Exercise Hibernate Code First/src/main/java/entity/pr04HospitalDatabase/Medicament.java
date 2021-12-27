package entity.pr04HospitalDatabase;

import entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "prescribed_medicamentations")
public class Medicament extends BaseEntity {
    private String medicamentName;
    private Diagnose diagnose;


    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    public Medicament() {
    }

    @Column(name = "medicament_name", length = 200)
    public String getMedicamentName() {
        return medicamentName;
    }

    public void setMedicamentName(String medicamentName) {
        this.medicamentName = medicamentName;
    }
}
