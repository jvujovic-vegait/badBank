# Fix and refactor

## Simple bank

The application is built using 2 classes.

Account class models how much money a person has. Two main methods are
deposit and withdraw. Account may never have negative deposit. (There is an if statement preventing that).
Bank class models registering account and getting existing account (by id).

Both classes have a lot of bugs.

## Simulation

There is a 3rd class BankRunner which has a main method.  
This class runs a simulation, where some number of accounts is created.
Then some random transfers are done. This is performed using multiple threads.  
After that the overall amount of money on accounts should not change.

This seems not to be the case.

## Task

Your job is to find bugs and other problems in the implementation.

1. Make corrections to the Account and Bank classes. (if you do not have time, please simply list the problems that you
   see).
2. You may also have to change BankRunner class! In such case, please try to preserve its intended logic.
3. Additionally try to fix or list other potential code quality issues that you see.
4. You are free to create additional classes or files as needed.

## Solution

First of all I have created a use case diagram in order to understand the business logic of the application and
distinguish the main actors.

![usecase.png](docs%2Fusecase.png)

Here I will note the most important things I have done to fix the code.

1. BankRunnner class was recreated as an integration test since really all it did was testing. The code stayed pretty
   much the same with some minor changes in order to stay true to original testing.
2. Account class was refactored to use BigDecimal instead of double. This was done in order to avoid floating point
   errors.
3. Account class was refactored to use synchronized methods instead of synchronized blocks. This was done in order to
   avoid deadlocks.
4. Account and Bank class were refactored to be data classes, while the business logic was moved to BankService class.
   This was done in order to separate the data from the logic. There is a possibility to use a Bank as a service and not
   a data class, but it made more sense to have as a data class in order to be able to create more than one bank.
5. Interfaces were used to define the method which are implemented in service classes.
6. Account number was moved to Account class since it is a property of the account and not the bank. Also, it was
   changed to be final since it should not be changed after creation.
7. I have added unit tests for Account and Bank classes.
8. Java doc was moved to service interfaces in order to be able to read the doc while using the service interface. While
   using some method, we don't necessarily need to know it's implementation, but we need to know what it does.

