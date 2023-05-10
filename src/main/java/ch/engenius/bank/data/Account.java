package ch.engenius.bank.data;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class Account {
    private BigDecimal money;
    //account number is specified per account, and it makes sense to move it as a field of the account
    private final int accountNumber;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}
