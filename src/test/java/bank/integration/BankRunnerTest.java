package bank.integration;

import ch.engenius.bank.data.Account;
import ch.engenius.bank.data.Bank;
import ch.engenius.bank.service.impl.AccountServiceImpl;
import ch.engenius.bank.service.impl.BankServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class BankRunnerTest {
    private static final ExecutorService executor = Executors.newFixedThreadPool(8);
    private final Random random = new Random(43);

    BankServiceImpl bankService;
    AccountServiceImpl accountService;
    Bank bank;

    @BeforeEach
    void setUp() {
        bankService = new BankServiceImpl();
        accountService = new AccountServiceImpl();
        bank = new Bank("TestBank");
    }

    @Test
    public void testBankRunner() {
        int accounts = 100;
        int defaultDeposit = 1000;
        int iterations = 10000;
        registerAccounts(accounts, defaultDeposit);
        assertEquals(0, sumAccounts().compareTo(
                BigDecimal.valueOf(accounts * defaultDeposit)));
        runBank(iterations, accounts);
        assertEquals(0, sumAccounts().compareTo(
                BigDecimal.valueOf(accounts * defaultDeposit)));
    }

    private void runBank(int iterations, int maxAccount) {
        for (int i = 0; i < iterations; i++) {
            executor.submit(() -> runRandomOperation(maxAccount));
        }
        try {
            executor.shutdown();
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.info("Interrupted exception while trying to shut down the executor", e);
        }
    }

    private void runRandomOperation(int maxAccount) {
        BigDecimal transferAmount = BigDecimal.valueOf(random.nextDouble()).multiply(BigDecimal.valueOf(100));
        int accountInNumber = random.nextInt(maxAccount);
        int accountOutNumber = random.nextInt(maxAccount);
        Account accIn = accountService.findAccountByNumber(bank, accountInNumber);
        Account accOut = accountService.findAccountByNumber(bank, accountOutNumber);
        accountService.transfer(accIn, accOut, transferAmount);
    }

    private void registerAccounts(int number, int defaultMoney) {
        for (int i = 0; i < number; i++) {
            bankService.registerAccount(bank, i, BigDecimal.valueOf(defaultMoney));
        }
    }

    private BigDecimal sumAccounts() {
        return bank.getAccounts().values().stream()
                .map(Account::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}