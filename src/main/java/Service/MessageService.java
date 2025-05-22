package Service;

import DAO.MessageDAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDao) {
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message addMessage(Message message){
        if(message == null){
            return null;
        }

        Message persisterMessage = messageDAO.addMessage(message);

        return persisterMessage;
    }

    

    
}
