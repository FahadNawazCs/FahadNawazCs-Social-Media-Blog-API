package DAO;

import Util.ConnectionUtil;
//import Model.Account;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // Retrieving all messages
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages =new ArrayList<>();
         try {
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Adding Message
    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        
         try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = pkeyResultSet.getInt(1);
                message.setMessage_id(generated_message_id);
            }
            return message;
           
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Get Messages by Account Id
    public List<Message> getMessagesByAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages =new ArrayList<>();

        try {
           String sql = "SELECT * FROM Message WHERE posted_by = ?";
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, account_id);
           ResultSet rs = preparedStatement.executeQuery();
           while(rs.next()){
               Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
               messages.add(message);
            }
        }catch(SQLException e){
           System.out.println(e.getMessage());
        }
       return messages;
    }

    // Get Message by message id
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message;
             }
         }catch(SQLException e){
            System.out.println(e.getMessage());
         }
        return null;
        
    }

    // Update message by message id
    public Message updateMessage(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();
            
         }catch(SQLException e){
            System.out.println(e.getMessage());
         }
        return null;
    }
    
    // Delete message by message id
    public boolean deleteMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            int affectedRows = preparedStatement.executeUpdate();


            return affectedRows > 0;
        } catch(SQLException e){
            System.out.println(e.getMessage());
         }
        
        return false;

    }
    
}

