package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;
import java.util.Objects;

import org.h2.util.StringUtils;

public class AccountService {
    private AccountDAO accountDAO;
    
    //Empty Constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    //Constructor with accountDAO
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // Get all Accounts
    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    // Registering a User
    public Account registerAccount(Account accountToInsert){

        if(StringUtils.isNullOrEmpty(accountToInsert.getUsername())){
            return null;
        }
        if(StringUtils.isNullOrEmpty(accountToInsert.getPassword()) || accountToInsert.getPassword().length() < 4){
            return null;
        }
        boolean accountExists = accountDAO.usernameExists(accountToInsert.getUsername());
        if(accountExists){
            return null;
        }
        Account insertedAccount = accountDAO.insertAccount(new Account(accountToInsert.getUsername(), accountToInsert.getPassword()));
        return insertedAccount;
    }

    // Verifying a User Login
    public Account userLogin(Account userToLogin){
        Account existingAccount = accountDAO.getAccount(userToLogin.getUsername(), userToLogin.getPassword());
        if(Objects.nonNull(existingAccount)){
            return existingAccount;
        }
        return null;
    }

}
