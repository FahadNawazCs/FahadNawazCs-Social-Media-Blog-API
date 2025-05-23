package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;

import Util.ConnectionUtil;
import Model.Message;
import Model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDao) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message addMessage(Message message){
        
        if(message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255 || accountDAO.idExists(message.getPosted_by()) == null){
            return null;
        }

        Message persisterMessage = messageDAO.addMessage(message);

        return persisterMessage;
    }

    public Message getMessageById(Message message){
        
    }

    
}
