package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    //retrieving all accounts from Account table 
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account getAccount(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        Account account = new Account();
        try {
            String sql = "SELECT * FROM Account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            account = new Account(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return account;
    }

    public boolean usernameExists(String username){
        Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "SELECT * FROM Account WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // If a row is returned, username exists
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return false; // Default to false on exception (could also throw)
    }
    }

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (id,username,password) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account.getAccount_id());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());

           ps.executeUpdate();
           return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
        return null;
        }
    }

