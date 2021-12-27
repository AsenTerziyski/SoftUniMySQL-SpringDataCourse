package entity.pr05BillsPaymentSystem;

import entity.BaseEntity;
import entity.pr05BillsPaymentSystem.BankUser;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetail extends BaseEntity {

    private String number;
    private BankUser owner;

    public BillingDetail() {
    }

    @Column(nullable = false, unique = true)
    public String getNumber() {
        return number;
    }

    @ManyToOne
    public BankUser getOwner() {
        return owner;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setOwner(BankUser owner) {
        this.owner = owner;
    }
}
