package ch.engenius.bank;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BankRunner {

    private static final ExecutorService executor = Executors.newFixedThreadPool(8);

    private final Random random = new Random(43);
    private final Bank bank = new Bank();


    public static void main(String[] args) {
        BankRunner runner = new BankRunner();
        int accounts = 100;
        int defaultDeposit = 1000;
        int iterations = 10000;
        runner.registerAccounts(accounts, defaultDeposit);
        runner.sanityCheck(accounts * defaultDeposit);
        runner.runBank(iterations, accounts);
        runner.sanityCheck(accounts * defaultDeposit);

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
        Account accIn = bank.getAccount(accountInNumber);
        Account accOut = bank.getAccount(accountOutNumber);
        accIn.transferTo(accOut, transferAmount);
    }

    private void registerAccounts(int number, int defaultMoney) {
        for (int i = 0; i < number; i++) {
            bank.registerAccount(i, BigDecimal.valueOf(defaultMoney));
        }
    }

    private void sanityCheck(int totalExpectedMoney) {
        BigDecimal sum = bank.getAccounts().values().stream()
                .map(Account::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sum.intValue() != totalExpectedMoney) {
            throw new IllegalStateException("We got " + sum + " != " + totalExpectedMoney + " (expected)");
        }

        log.info("Sanity check passed");
    }


}
