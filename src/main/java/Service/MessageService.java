package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;


import Model.Message;
import Model.Account;

import static org.mockito.ArgumentMatchers.notNull;

import java.util.List;


public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    // public MessageService(MessageDAO messageDao) {
    //     this.messageDAO = messageDAO;
    //     this.accountDAO = accountDAO;
    // }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // Creating new message
    public Message addMessage(Message message){
        
        if(message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255 || accountDAO.idExists(message.getPosted_by()) == null){
            return null;
        }

        Message persisterMessage = messageDAO.addMessage(message);

        return persisterMessage;
    }

    // Getting message by message id
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
        
    }

    // Gettin All messages by specific user
    public List<Message> getAllMessagesByAccountId(int account_id){
        return messageDAO.getMessagesByAccountId(account_id);
    }

    // Updating Message by message id
    public Message updateMessage(int message_id, Message message){
        String newText = message.getMessage_text();
        Message existingMessage = messageDAO.getMessageById(message_id);

        if(newText.isEmpty() || newText.length() > 255 || existingMessage == null){
            return null;
        }

        return messageDAO.updateMessage(message_id, newText);
    }

    public Message deletMessage(int message_id){

        return messageDAO.deleteMessage(message_id);
    }
        
    
}
