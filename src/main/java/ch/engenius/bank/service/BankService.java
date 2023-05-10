package ch.engenius.bank.service;

import ch.engenius.bank.data.Bank;

import java.math.BigDecimal;

public interface BankService {
    /**
     * Registers account in the bank.
     *
     * @param bank          bank where account should be registered
     * @param accountNumber account number
     * @param amount        initial money amount on the account
     * @throws IllegalStateException if account with the same number is already registered or amount is negative
     */
    Bank registerAccount(Bank bank, int accountNumber, BigDecimal amount);
}
