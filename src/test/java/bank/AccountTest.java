package bank;

import ch.engenius.bank.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account account;
    BigDecimal INITIAL_AMOUNT = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setMoney(INITIAL_AMOUNT);
        account.setAccountNumber(1);
    }

    @Test
    void testNegativeWithdraw() {
        assertThrows(IllegalStateException.class, () -> account.withdraw(new BigDecimal(-1)));
    }

    @Test
    void testWithdrawAmountBiggerThanAccountBalance() {
        assertThrows(IllegalStateException.class, () -> account.withdraw(INITIAL_AMOUNT.add(new BigDecimal(1))));
    }

    @Test
    void testNegativeDeposit() {
        assertThrows(IllegalStateException.class, () -> account.deposit(new BigDecimal(-1)));
    }

    @Test
    void testWithdraw() {
        account.withdraw(new BigDecimal(100));
        assertEquals(INITIAL_AMOUNT.subtract(new BigDecimal(100)), account.getMoney());
    }

    @Test
    void testDeposit() {
        account.deposit(new BigDecimal(100));
        assertEquals(INITIAL_AMOUNT.add(new BigDecimal(100)), account.getMoney());
    }

    @Test
    void testTransferTo() {
        Account other = new Account();
        other.setMoney(new BigDecimal(0));
        other.setAccountNumber(2);
        account.transferTo(other, new BigDecimal(100));
        assertTrue(account.getMoney().compareTo(INITIAL_AMOUNT.subtract(new BigDecimal(100))) == 0);
        assertTrue(other.getMoney().compareTo(new BigDecimal(100)) == 0);
    }

    @Test
    void testTransferToNegativeAmount() {
        Account other = new Account();
        other.setMoney(new BigDecimal(0));
        assertThrows(IllegalStateException.class, () -> account.transferTo(other, new BigDecimal(-100)));
    }

    @Test
    void testTransferToNotEnoughMoney() {
        Account other = new Account();
        other.setMoney(new BigDecimal(0));
        assertThrows(IllegalStateException.class, () -> account.transferTo(other, INITIAL_AMOUNT.add(new BigDecimal(1))));
    }

    @Test
    void testTransferToSameAccount() {
        assertThrows(IllegalStateException.class, () -> account.transferTo(account, new BigDecimal(100)));
    }


}
