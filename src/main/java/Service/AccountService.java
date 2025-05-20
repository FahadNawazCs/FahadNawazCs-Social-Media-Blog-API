package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public Account getAccount(String username, String password){
        return accountDAO.getAccount(username, password);
    }

    public Account registerAccount(Account account, String username, String password){

        if(account.getUsername() != null && 
        !account.getUsername().isBlank() &&
        account.getPassword() != null &&
        account.getPassword().length() >= 4 &&
        !accountDAO.usernameExists(account.getUsername())){
            return accountDAO.insertAccount(account);
        }
    }




}
