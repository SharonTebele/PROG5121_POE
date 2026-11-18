package src.main.java.com.PROG5121.POE_Part1;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        String message =
                "Hi Mike, can you please come to the office to discuss?";

        assertTrue(message.length() <= 250);
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "a".repeat(300);
        assertTrue(longMessage.length() > 250);
    }

    @Test
    public void testRecipientValid() {
        Message message = new Message(
                "+27718693002", "Test", 0);

        assertEquals(1, message.checkRecipientCellTest());
    }

    @Test
    public void testRecipientInvalid() {
        Message message = new Message(
                "08575975889", "Test", 0);

        assertEquals(0, message.checkRecipientCellTest());
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                "+27718693002", "Hi Mike, can you please come", 0);

        message.messageID = "0011223344";

        String hash = message.createMessageHash();

        assertEquals("00:0:HICOME", hash);
    }

    @Test
    public void testMessageIDLength() {
        Message message = new Message(
                "+27718693002", "Test", 0);

        assertTrue(message.checkMessageID());
    }

    @Test
    public void testTotalMessages() {
        Message.totalMessages = 2;
        Message message = new Message();

        assertEquals(2, message.returnTotalMessages());
    }
}