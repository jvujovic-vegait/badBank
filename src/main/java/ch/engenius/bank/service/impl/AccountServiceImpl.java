package ch.engenius.bank.service.impl;

import ch.engenius.bank.data.Account;
import ch.engenius.bank.data.Bank;
import ch.engenius.bank.service.AccountService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class AccountServiceImpl implements AccountService {
    @Override
    public synchronized Account findAccountByNumber(Bank bank, int number) {
        Objects.requireNonNull(bank, "Bank cannot be null");

        Map<Integer, Account> accounts = bank.getAccounts();
        if (!accounts.containsKey(number)) {
            throw new IllegalArgumentException("Account with number " + number + " is not registered");
        }
        return accounts.get(number);
    }

    @Override
    public void withdraw(Account account, BigDecimal amount) {
        Objects.requireNonNull(account, "Account cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Amount to be withdrawn must be positive");
        }

        if (!hasEnoughMoney(account, amount)) {
            throw new IllegalStateException("not enough credits on account");
        }

        account.setMoney(account.getMoney().subtract(amount));
    }

    @Override
    public boolean hasEnoughMoney(Account account, BigDecimal amount) {
        Objects.requireNonNull(account, "Account cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        return account.getMoney().subtract(amount).signum() >= 0;
    }

    @Override
    public synchronized void deposit(Account account, BigDecimal amount) {
        Objects.requireNonNull(account, "Account cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Amount to be deposited must be positive");
        }
        account.setMoney(account.getMoney().add(amount));
    }

    @Override
    public synchronized void transfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        Objects.requireNonNull(fromAccount, "From account cannot be null");
        Objects.requireNonNull(toAccount, "To account cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Amount to be transferred must be positive");
        }
        if (fromAccount.getAccountNumber() == toAccount.getAccountNumber()) {
            throw new IllegalStateException("Cannot transfer money to the same account");
        }
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
    }
}
