package bank;

import ch.engenius.bank.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void testRegisterAccount() {
        bank.registerAccount(1, new BigDecimal(1000));
        assertTrue(bank.getAccounts().containsKey(1));
    }

    @Test
    void testRegisterAccountTwice() {
        bank.registerAccount(1, new BigDecimal(1000));
        assertThrows(IllegalStateException.class, () -> bank.registerAccount(1, new BigDecimal(1000)));
    }

    @Test
    void testGetAccountNotRegistered() {
        assertThrows(IllegalArgumentException.class, () -> bank.getAccount(1));
    }

    @Test
    void testGetAccount() {
        bank.registerAccount(1, new BigDecimal(1000));
        assertNotNull(bank.getAccount(1));
    }
}
