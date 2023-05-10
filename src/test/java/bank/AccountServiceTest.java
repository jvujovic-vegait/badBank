package bank;

import ch.engenius.bank.data.Account;
import ch.engenius.bank.data.Bank;
import ch.engenius.bank.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {
    Account account;
    BigDecimal INITIAL_AMOUNT = new BigDecimal(1000);
    AccountServiceImpl underTestService;

    Bank bank;

    @BeforeEach
    void setUp() {
        account = new Account(INITIAL_AMOUNT, 1);
        underTestService = new AccountServiceImpl();
        bank = new Bank("TestBank");
    }

    @Test
    void testNegativeWithdraw() {
        BigDecimal negativeAmount = new BigDecimal(-1);
        assertThrows(IllegalStateException.class, () -> underTestService.withdraw(account, negativeAmount));
    }

    @Test
    void testWithdrawAmountBiggerThanAccountBalance() {
        BigDecimal amountToBeWithdrawn = INITIAL_AMOUNT.add(new BigDecimal(1));
        assertThrows(IllegalStateException.class, () -> underTestService.withdraw(account, amountToBeWithdrawn));
    }

    @Test
    void testNegativeDeposit() {
        BigDecimal negativeAmount = new BigDecimal(-1);
        assertThrows(IllegalStateException.class, () -> underTestService.deposit(account, negativeAmount));
    }

    @Test
    void testWithdraw() {
        underTestService.withdraw(account, new BigDecimal(100));
        assertEquals(INITIAL_AMOUNT.subtract(new BigDecimal(100)), account.getMoney());
    }

    @Test
    void testDeposit() {
        underTestService.deposit(account, new BigDecimal(100));
        assertEquals(INITIAL_AMOUNT.add(new BigDecimal(100)), account.getMoney());
    }

    @Test
    void testTransferTo() {
        Account other = new Account(new BigDecimal(0), 2);
        underTestService.transfer(account, other, new BigDecimal(100));
        assertEquals(0, account.getMoney()
                .compareTo(INITIAL_AMOUNT.subtract(new BigDecimal(100))));
        assertEquals(0, other.getMoney()
                .compareTo(new BigDecimal(100)));
    }

    @Test
    void testTransferToNegativeAmount() {
        Account other = new Account(new BigDecimal(0), 3);
        BigDecimal negativeAmount = new BigDecimal(-100);
        assertThrows(IllegalStateException.class, () ->
                underTestService.transfer(account, other, negativeAmount));
    }

    @Test
    void testTransferToNotEnoughMoney() {
        Account other = new Account(new BigDecimal(0), 4);
        BigDecimal amount = INITIAL_AMOUNT.add(new BigDecimal(1));
        assertThrows(IllegalStateException.class, () ->
                underTestService.transfer(account, other, amount));
    }

    @Test
    void testTransferToSameAccount() {
        BigDecimal amount = new BigDecimal(100);
        assertThrows(IllegalStateException.class, () ->
                underTestService.transfer(account, account, amount));
    }

    @Test
    void testGetAccountNotRegistered() {
        assertThrows(IllegalArgumentException.class, () ->
                underTestService.findAccountByNumber(bank, 2));
    }
}
