package entity.pr05BillsPaymentSystem;

import entity.pr05BillsPaymentSystem.BillingDetail;
import entity.pr05BillsPaymentSystem.CardType;

import javax.persistence.*;


@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {

    public CardType cardType;
    private int expirationMonth;
    private int expirationYear;

    public CreditCard() {
    }

    @Enumerated(EnumType.STRING)
    public CardType getCardType() {
        return cardType;
    }

    @Column (name = "expiration_month")
    public int getExpirationMonth() {
        return expirationMonth;
    }

    @Column (name = "expiration_year")
    public int getExpirationYear() {
        return expirationYear;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
}
