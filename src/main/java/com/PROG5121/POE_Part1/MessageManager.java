package src.main.java.com.PROG5121.POE_Part1;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageManager {

    private final ArrayList<Message> sentMessages = new ArrayList<>();
    private final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private final ArrayList<Message> storedMessages = new ArrayList<>();

    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>();

    private final Path storageFile = Path.of("storedMessages.json");

    public void addMessage(Message message, Message.Status status) {

        if (message == null || status == null) {
            throw new IllegalArgumentException(
                    "Message and status cannot be null.");
        }

        message.setStatus(status);

        if (!messageIDs.contains(message.getMessageID())) {
            messageIDs.add(message.getMessageID());
        }

        if (!messageHashes.contains(message.getMessageHash())) {
            messageHashes.add(message.getMessageHash());
        }

        switch (status) {

            case SENT:
                sentMessages.add(message);
                Message.messagesSent++;
                Message.totalMessages++;
                break;

            case STORED:
                storedMessages.add(message);
                saveStoredMessages();
                break;

            case DISREGARDED:
                disregardedMessages.add(message);
                break;
        }
    }

    public String displayStoredSendersAndRecipients() {

        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }

        StringBuilder output = new StringBuilder();

        for (Message message : storedMessages) {
            output.append("Sender: ")
                    .append(message.getSender())
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\n\n");
        }

        return output.toString();
    }

    public Message getLongestStoredMessage() {

        Message longest = null;

        for (Message message : storedMessages) {
            if (longest == null
                    || message.getMessageText().length()
                    > longest.getMessageText().length()) {
                longest = message;
            }
        }

        return longest;
    }

    public Message searchByMessageID(String id) {

        for (Message message : sentMessages) {
            if (message.getMessageID().equals(id)) {
                return message;
            }
        }

        for (Message message : storedMessages) {
            if (message.getMessageID().equals(id)) {
                return message;
            }
        }

        for (Message message : disregardedMessages) {
            if (message.getMessageID().equals(id)) {
                return message;
            }
        }

        return null;
    }

    public ArrayList<Message> searchByRecipient(String recipient) {

        ArrayList<Message> results = new ArrayList<>();

        for (Message message : sentMessages) {
            if (message.getRecipient().equals(recipient)) {
                results.add(message);
            }
        }

        for (Message message : storedMessages) {
            if (message.getRecipient().equals(recipient)) {
                results.add(message);
            }
        }

        return results;
    }

    public boolean deleteStoredMessageByHash(String hash) {

        for (int i = 0; i < storedMessages.size(); i++) {

            Message message = storedMessages.get(i);

            if (message.getMessageHash().equals(hash)) {

                storedMessages.remove(i);
                messageHashes.remove(hash);
                messageIDs.remove(message.getMessageID());

                saveStoredMessages();
                return true;
            }
        }

        return false;
    }

    public String displayStoredMessageReport() {

        if (storedMessages.isEmpty()) {
            return "There are no stored messages to report.";
        }

        StringBuilder report = new StringBuilder();

        report.append("========== STORED MESSAGE REPORT ==========\n");

        for (Message message : storedMessages) {

            report.append("Sender: ")
                    .append(message.getSender()).append("\n");

            report.append("Message ID: ")
                    .append(message.getMessageID()).append("\n");

            report.append("Message Hash: ")
                    .append(message.getMessageHash()).append("\n");

            report.append("Recipient: ")
                    .append(message.getRecipient()).append("\n");

            report.append("Message: ")
                    .append(message.getMessageText()).append("\n");

            report.append("-------------------------------------------\n");
        }

        return report.toString();
    }

    public void saveStoredMessages() {

        JSONArray jsonArray = new JSONArray();

        for (Message message : storedMessages) {

            JSONObject object = new JSONObject();

            object.put("sender", message.getSender());
            object.put("id", message.getMessageID());
            object.put("hash", message.getMessageHash());
            object.put("recipient", message.getRecipient());
            object.put("message", message.getMessageText());
            object.put("num", message.getNumMessages());
            object.put("status", message.getStatus().name());

            jsonArray.put(object);
        }

        try {
            Files.writeString(storageFile, jsonArray.toString(4));
        } catch (IOException e) {
            System.out.println("Could not save stored messages: "
                    + e.getMessage());
        }
    }

    public void loadStoredMessages() {

        if (!Files.exists(storageFile)) {
            return;
        }

        try {
            String content = Files.readString(storageFile);

            if (content.isBlank()) {
                return;
            }

            JSONArray jsonArray = new JSONArray(content);

            storedMessages.clear();
            messageIDs.clear();
            messageHashes.clear();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);

                String sender = object.optString(
                        "sender", "QuickChat user");

                String id = object.getString("id");
                String hash = object.getString("hash");
                String recipient = object.getString("recipient");
                String text = object.getString("message");
                int number = object.optInt("num", i);

                Message message = new Message(
                        sender, recipient, text, number);

                message.messageID = id;
                message.messageHash = hash;
                message.setStatus(Message.Status.STORED);

                storedMessages.add(message);

                if (!messageIDs.contains(id)) {
                    messageIDs.add(id);
                }

                if (!messageHashes.contains(hash)) {
                    messageHashes.add(hash);
                }
            }

        } catch (Exception e) {
            System.out.println("Could not read stored messages: "
                    + e.getMessage());
        }
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    public List<Message> getStoredMessages() {
        return storedMessages;
    }

    public List<String> getMessageHashes() {
        return messageHashes;
    }

    public List<String> getMessageIDs() {
        return messageIDs;
    }
}