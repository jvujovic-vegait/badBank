package bank;

import ch.engenius.bank.data.Bank;
import ch.engenius.bank.service.impl.BankServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankServiceTest {

    Bank bank;
    BankServiceImpl underTestService;

    @BeforeEach
    void setUp() {
        bank = new Bank("Test Bank");
        underTestService = new BankServiceImpl();
    }

    @Test
    void testRegisterAccount() {
        underTestService.registerAccount(bank, 1, new BigDecimal(1000));
        assertTrue(bank.getAccounts().containsKey(1));
    }

    @Test
    void testRegisterAccountTwice() {
        BigDecimal amount = new BigDecimal(1000);
        int accountNumber = 1;
        underTestService.registerAccount(bank, accountNumber, amount);
        assertThrows(IllegalStateException.class, () ->
                underTestService.registerAccount(bank, accountNumber, amount));
    }
}
