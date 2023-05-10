package ch.engenius.bank.service.impl;

import ch.engenius.bank.data.Account;
import ch.engenius.bank.data.Bank;
import ch.engenius.bank.service.BankService;

import java.math.BigDecimal;
import java.util.Objects;

public class BankServiceImpl implements BankService {
    @Override
    public synchronized Bank registerAccount(Bank bank, int accountNumber, BigDecimal amount) {
        Objects.requireNonNull(bank, "Bank cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Amount to be deposited must be positive");
        }

        if (bank.getAccounts().containsKey(accountNumber)) {
            throw new IllegalStateException("Account with number " + accountNumber + " is already registered");
        }
        Account account = new Account(amount, accountNumber);
        bank.getAccounts().put(accountNumber, account);
        return bank;
    }
}
