package entity.pr05BillsPaymentSystem;

import entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "bank_users")
public class BankUser extends BaseEntity {

    private String firstName;
    private String lastName;
    private String password;
    private String email;

    @Column (name = "first_name", nullable = false, length = 100)
    public String getFirstName() {
        return firstName;
    }

    @Column (name = "last_name", nullable = false, length = 100)
    public String getLastName() {
        return lastName;
    }

    @Column (name = "password", nullable = false, length = 150)
    public String getPasswordName() {
        return password;
    }

    @Column (name = "email", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPasswordName(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
