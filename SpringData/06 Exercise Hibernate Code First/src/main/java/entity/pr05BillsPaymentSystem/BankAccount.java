package entity.pr05BillsPaymentSystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetail {

    private String name;
    private String swift;

    public BankAccount() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public String getSwift() {
        return swift;
    }
}
