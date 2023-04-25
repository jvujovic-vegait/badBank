package bank;

import ch.engenius.bank.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account underTest;
    BigDecimal INITIAL_AMOUNT = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        underTest = new Account();
        underTest.setMoney(INITIAL_AMOUNT);
        underTest.setAccountNumber(1);
    }

    @Test
    void testNegativeWithdraw() {
        BigDecimal negativeAmount = new BigDecimal(-1);
        assertThrows(IllegalStateException.class, () -> underTest.withdraw(negativeAmount));
    }

    @Test
    void testWithdrawAmountBiggerThanAccountBalance() {
        BigDecimal amountToBeWithdrawn = INITIAL_AMOUNT.add(new BigDecimal(1));
        assertThrows(IllegalStateException.class, () -> underTest.withdraw(amountToBeWithdrawn));
    }

    @Test
    void testNegativeDeposit() {
        BigDecimal negativeAmount = new BigDecimal(-1);
        assertThrows(IllegalStateException.class, () -> underTest.deposit(negativeAmount));
    }

    @Test
    void testWithdraw() {
        underTest.withdraw(new BigDecimal(100));
        assertEquals(INITIAL_AMOUNT.subtract(new BigDecimal(100)), underTest.getMoney());
    }

    @Test
    void testDeposit() {
        underTest.deposit(new BigDecimal(100));
        assertEquals(INITIAL_AMOUNT.add(new BigDecimal(100)), underTest.getMoney());
    }

    @Test
    void testTransferTo() {
        Account other = new Account();
        other.setMoney(new BigDecimal(0));
        other.setAccountNumber(2);
        underTest.transferTo(other, new BigDecimal(100));
        assertEquals(0, underTest.getMoney()
                .compareTo(INITIAL_AMOUNT.subtract(new BigDecimal(100))));
        assertEquals(0, other.getMoney()
                .compareTo(new BigDecimal(100)));
    }

    @Test
    void testTransferToNegativeAmount() {
        Account other = new Account();
        other.setMoney(new BigDecimal(0));
        BigDecimal negativeAmount = new BigDecimal(-100);
        assertThrows(IllegalStateException.class, () -> underTest.transferTo(other, negativeAmount));
    }

    @Test
    void testTransferToNotEnoughMoney() {
        Account other = new Account();
        other.setMoney(new BigDecimal(0));
        BigDecimal amount = INITIAL_AMOUNT.add(new BigDecimal(1));
        assertThrows(IllegalStateException.class, () -> underTest.transferTo(other, amount));
    }

    @Test
    void testTransferToSameAccount() {
        BigDecimal amount = new BigDecimal(100);
        assertThrows(IllegalStateException.class, () -> underTest.transferTo(underTest, amount));
    }


}
