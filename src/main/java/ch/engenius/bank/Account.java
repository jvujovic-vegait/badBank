package ch.engenius.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private BigDecimal money;
    //account number is specified per account, and it makes sense to move it as a field of the account
    private int accountNumber;

    /**
     * Withdraws money amount from the account.
     *
     * @param amount money amount to be withdrawn from the account
     * @throws IllegalStateException if there is not enough money from the account to be withdrawn
     */
    public synchronized void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Amount to be withdrawn must be positive");
        }

        if (!hasEnoughMoney(amount)) {
            throw new IllegalStateException("not enough credits on account");
        }

        setMoney(money.subtract(amount));
    }

    private boolean hasEnoughMoney(BigDecimal amount) {
        return money.subtract(amount).signum() >= 0;
    }

    /**
     * Deposits money amount to the account.
     *
     * @param amount money amount to be deposited to the account
     * @throws IllegalStateException if amount to be deposited is negative
     */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Amount to be deposited must be positive");
        }
        synchronized (this) {
            setMoney(money.add(amount));
        }
    }

    /**
     * Transfers money amount from this account to another account.
     *
     * @param account account to transfer money to
     * @param amount  money amount to be transferred
     * @throws IllegalStateException if amount to be transferred is negative
     */
    public void transferTo(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Amount to be transferred must be positive");
        }
        if (this.accountNumber == account.getAccountNumber()) {
            throw new IllegalStateException("Cannot transfer money to the same account");
        }
        this.withdraw(amount);
        account.deposit(amount);
    }

    public synchronized BigDecimal getMoney() {
        return money;
    }
}
