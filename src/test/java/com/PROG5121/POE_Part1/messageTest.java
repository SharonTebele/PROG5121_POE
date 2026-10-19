package src.test.java.com.PROG5121.POE_Part1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    @Test
    public void testMessageLengthSuccess() {
        String msg = "Hi Mike, can you please come to the office to discuss?";
        assertTrue(msg.length() < 250);
    }
    @Test
    public void testMessageLengthFailure() {
        String longMsg = "a".repeat(300);
        assertTrue(longMsg.length() > 250);
    }
    @Test
    public void testRecipientValid() {
        Message m = new Message("+27718693002", "Test", 0);
        assertEquals(1, m.checkRecipientCellTest());
    }
    @Test
    public void testRecipientInvalid() {
        Message m = new Message("08575975889", "Test", 0);
        assertEquals(0, m.checkRecipientCellTest());
    }
    @Test
    public void testMessageHash() {
        Message m = new Message("+27718693002", "Hi Mike, can you please come", 0);
        m.messageID = "0011223344";
        String hash = m.createMessageHash();
        // Expected: 00:0:HICOME
        assertEquals("00:0:HICOME", hash);
    }
    @Test
    public void testMessageIDLength() {
        Message m = new Message("+27718693002", "Test", 0);
        assertTrue(m.checkMessageID());
    }
    @Test
    public void testTotalMessages() {
        Message.totalMessages = 2;
        Message m = new Message();
        assertEquals(2, m.returnTotalMessages());
    }
}
