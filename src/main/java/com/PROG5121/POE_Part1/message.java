package src.main.java.com.PROG5121.POE_Part1;

import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Message{
    String messageID;
    int numMessages;
    String recipient;
    String messageText;
    String messageHash;

    static int totalMessage = 0;
    static JSONArray allMessages = new JSONArray();
    static int messageSent = 0;

    public Message(String recipient, String messageText, int numMessages){
        this.messageID = generateID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.numMessages = numMessages;
    }
public Message() {}
private String generateID(){
    Random rand = new Random();
    long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
    return String.valueOf(id);
}

    public boolean checkMessageID(){
        return messageID.length() <= 10;
    }
    public int checkRecipientCellTest(){
        if(recipient != null && recipient.length()<=12 && recipient.matches("^\\+27[0-9]{9}$")){
            return 1;
        }
        return 0;
    }

    public String checkRecipientCell(){
        if(checkRecipientCell() == 1) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    public String createMessageHash(){
        String[] words = messageText.split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length-1].toUpperCase().replaceA11("[^A-Z0-9]", "");
        String firstTwo = messageID.substring(0,2);
        return firstTwo + ":" + numMessages + ":" + firstWord + lastWord;
                return this.messageHash;
    }

    public String SentMessage(int choice){
        Switch (choice) {
        case 1: return "Message successfully stored.";
        case 2: return "Press to delete the message.";
        case 3: return "Message successfully stored.";
        default: return "Invalid choice.";
        }
    }

    public String printMessages(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < all messages.length(); i++){
            JSONObject obj = allMessages.getJSONObject(i);
            sb.append("MessageID: ").append(obj.getString("id"))
             .append("\nHash: ").append(obj.getString("hash"))
              .append("\nRecipient: ").append(obj.getString("recipient"))
              .append("\nMessage: ").append(obj.getString("message")).append("\n\n");
        }
        return sb.toString();
    }
        
    public int returnTotalMessages(){
        return totalMessage;
    }

    public void storeMessage() {
        JSONObject obj = new JSONObject();
        obj.put("id", messageID);
        obj.put("hash", messageHash);
        obj.put("recipient", recipient);
        obj.put("message", messageText);
        obj.put("num", numMessages);

        allMessages.put(obj);
        totalMessages++;

        try (FileWriter file = new FileWriter("storedMessages.json")) {
            file.write(allMessages.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadMessages() {
        try {
            if (Files.exists(Paths.get("storedMessages.json"))) {
                String content = new String(Files.readAllBytes(Paths.get("storedMessages.json")));
                if(!content.isEmpty()) allMessages = new JSONArray(content);
            }
        } catch (Exception e) {}
    }
    }
