package bank;

import ch.engenius.bank.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank underTest;

    @BeforeEach
    void setUp() {
        underTest = new Bank();
    }

    @Test
    void testRegisterAccount() {
        underTest.registerAccount(1, new BigDecimal(1000));
        assertTrue(underTest.getAccounts().containsKey(1));
    }

    @Test
    void testRegisterAccountTwice() {
        BigDecimal amount = new BigDecimal(1000);
        int accountNumber = 1;
        underTest.registerAccount(accountNumber, amount);
        assertThrows(IllegalStateException.class, () -> underTest.registerAccount(accountNumber, amount));
    }

    @Test
    void testGetAccountNotRegistered() {
        assertThrows(IllegalArgumentException.class, () -> underTest.getAccount(1));
    }

    @Test
    void testGetAccount() {
        underTest.registerAccount(1, new BigDecimal(1000));
        assertNotNull(underTest.getAccount(1));
    }
}
