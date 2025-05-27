 package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Objects;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register" , this::registerAccountHandler);
        app.post("/login", this::userLoginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getAllMessagesByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        
        return app;
    }

    private void registerAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account accountToInsert = mapper.readValue(ctx.body(), Account.class);
        Account insertedAccount = accountService.registerAccount(accountToInsert);
        if(Objects.nonNull(insertedAccount)){
            ctx.json(mapper.writeValueAsString(insertedAccount));
            ctx.status(200);
        }
        else{
            ctx.status(400);
        }
    }

    private void userLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account userToLogin = mapper.readValue(ctx.body(), Account.class);
        Account loggedInUser = accountService.userLogin(userToLogin);
        if(Objects.nonNull(loggedInUser)){
            ctx.json(mapper.writeValueAsString(loggedInUser));
            ctx.status(200);
        }
        else{
            ctx.status(401);
        }

    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        Message persistedMessage = messageService.addMessage(message);
        if(Objects.nonNull(persistedMessage)){
            ctx.json(mapper.writeValueAsString(persistedMessage));
            ctx.status(200);
        }
        else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        
    }
    
    private void getAllMessagesByIdHandler(Context ctx) throws JsonProcessingException{
       int messageId = Integer.parseInt(ctx.pathParam("message_id"));
       Message message = messageService.getMessageById(messageId);
       if(message != null){
        ctx.json(message);
       }else {
        ctx.result("");
        
       }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));

        Message messageUpdate = mapper.readValue(ctx.body(), Message.class);

        Message updatedMessage = messageService.updateMessage(messageId, messageUpdate);

        if(updatedMessage != null){
            ctx.json(updatedMessage);
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context ctx)throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));

        Message deletedMessage = messageService.deletMessage(messageId);

        if(deletedMessage != null){
            ctx.json(deletedMessage);
        }else{
            ctx.result("");
        }
        ctx.status(200);
     }

     private void getAllMessagesByAccountIdHandler(Context ctx)throws JsonProcessingException{
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        ctx.json(messages);
     }

}