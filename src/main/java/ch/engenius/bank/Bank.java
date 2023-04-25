package ch.engenius.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class Bank {
    private final Map<Integer, Account> accounts = new HashMap<>();

    /**
     * Registers account in the bank.
     *
     * @param accountNumber account number
     * @param amount        initial money amount on the account
     * @throws IllegalStateException if account with the same number is already registered
     */
    public synchronized Account registerAccount(int accountNumber, BigDecimal amount) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalStateException("Account with number " + accountNumber + " is already registered");
        }
        Account account = new Account(amount, accountNumber);
        accounts.put(accountNumber, account);
        return account;
    }

    /**
     * Returns account from specified number.
     *
     * @param number account number
     * @return account
     * @throws IllegalStateException if account with the specified number is not registered
     */

    public synchronized Account getAccount(int number) {
        if (!accounts.containsKey(number)) {
            throw new IllegalArgumentException("Account with number " + number + " is not registered");
        }
        return accounts.get(number);
    }
}
