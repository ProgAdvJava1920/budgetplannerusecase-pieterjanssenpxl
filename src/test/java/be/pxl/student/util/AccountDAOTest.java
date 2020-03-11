package be.pxl.student.util;

import be.pxl.student.DAO.AccountDAO;
import be.pxl.student.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AccountDAOTest {
    @Test
    public void testAccountInsert() {
        Account account = new Account();
        account.setIBAN("testIBAN");
        account.setName("testName");

        AccountDAO accountDAO = new AccountDAO("jdbc:mariadb://localhost:3306/budgetplanner", "root", "admin");
        Account accountInserted = accountDAO.createAccount(account);
        Assertions.assertNotEquals(0, accountInserted.getId());
        System.out.println(accountInserted.getId());
    }
}