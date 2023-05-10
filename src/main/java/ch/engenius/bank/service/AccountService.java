package ch.engenius.bank.service;

import ch.engenius.bank.data.Account;
import ch.engenius.bank.data.Bank;

import java.math.BigDecimal;

public interface AccountService {
    /**
     * Find account with specified number.
     *
     * @param bank   bank where account should be searched
     * @param number account number
     * @return account
     * @throws IllegalStateException if account with the specified number is not registered
     */
    Account findAccountByNumber(Bank bank, int number);

    /**
     * Withdraws money amount from the account.
     *
     * @param account account from which money should be withdrawn
     * @param amount  money amount to be withdrawn from the account
     * @throws IllegalStateException if there is not enough money from the account to be withdrawn
     */
    void withdraw(Account account, BigDecimal amount);

    boolean hasEnoughMoney(Account account, BigDecimal amount);

    /**
     * Deposits money amount to the account.
     *
     * @param account account to which money should be deposited
     * @param amount  money amount to be deposited to the account
     * @throws IllegalStateException if amount to be deposited is negative
     */
    void deposit(Account account, BigDecimal amount);

    /**
     * Transfers money amount from this account to another account.
     *
     * @param fromAccount account to transfer money from
     * @param toAccount   account to transfer money to
     * @param amount      money amount to be transferred
     * @throws IllegalStateException if amount to be transferred is negative
     */
    void transfer(Account fromAccount, Account toAccount, BigDecimal amount);
}
