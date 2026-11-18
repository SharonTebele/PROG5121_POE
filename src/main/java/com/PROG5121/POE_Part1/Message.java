package src.main.java.com.PROG5121.POE_Part1;


public class Message {

    String messageID;
    int numMessages;
    String recipient;
    String messageText;
    String messageHash;
    String sender;

    public static int totalMessages = 0;
    public static int messagesSent = 0;

    public enum Status {
        SENT,
        STORED,
        DISREGARDED
    }

    Status status;

    public Message() {
    }

    public Message(String recipient, String messageText, int numMessages) {
        this("QuickChat user", recipient, messageText, numMessages);
    }

    public Message(String sender, String recipient,
                   String messageText, int numMessages) {

        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.numMessages = numMessages;
        this.messageID = generateID();
        this.status = Status.SENT;
        this.messageHash = createMessageHash();
    }

    private String generateID() {

        long number = java.util.concurrent.ThreadLocalRandom
                .current()
                .nextLong(1_000_000_000L, 10_000_000_000L);

        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("[0-9]{10}");
    }

    public int checkRecipientCellTest() {

        if (recipient != null
                && recipient.matches("^\\+27[0-9]{9}$")) {
            return 1;
        }

        return 0;
    }

    public String checkRecipientCell() {

        if (checkRecipientCellTest() == 1) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted.";
    }

    public String createMessageHash() {

        if (messageID == null || messageText == null
                || messageText.isBlank()) {
            return "";
        }

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0]
                .replaceAll("[^A-Za-z0-9]", "").toUpperCase();

        String lastWord = words[words.length - 1]
                .replaceAll("[^A-Za-z0-9]", "").toUpperCase();

        String firstTwoDigits = messageID.substring(0, 2);

        messageHash = firstTwoDigits + ":"
                + numMessages + ":"
                + firstWord + lastWord;

        return messageHash;
    }

    public String SentMessage(int choice) {

        switch (choice) {

            case 1:
                status = Status.SENT;
                return "Message successfully sent.";

            case 2:
                status = Status.DISREGARDED;
                return "Message disregarded.";

            case 3:
                status = Status.STORED;
                return "Message successfully stored.";

            default:
                return "Invalid choice.";
        }
    }

    public int returnTotalMessages() {
        return totalMessages;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getSender() {
        return sender;
    }

    public int getNumMessages() {
        return numMessages;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}