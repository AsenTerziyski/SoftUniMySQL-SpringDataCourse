package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {


    private Long Id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
