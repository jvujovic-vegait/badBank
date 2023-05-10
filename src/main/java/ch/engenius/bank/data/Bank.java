package ch.engenius.bank.data;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Bank {
    private String name;
    private final Map<Integer, Account> accounts = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }
}
