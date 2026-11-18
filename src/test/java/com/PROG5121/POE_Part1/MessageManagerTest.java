package src.main.java.com.PROG5121.POE_Part1;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {

    private MessageManager manager;

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @BeforeEach
    public void setUp() {

        manager = new MessageManager();

        message1 = new Message(
                "Developer",
                "+27834557896",
                "Did you get the cake?",
                0);

        message2 = new Message(
                "Developer",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                1);

        message3 = new Message(
                "Developer",
                "+27834484567",
                "Yohoooo, I am at your gate.",
                2);

        message4 = new Message(
                "Developer",
                "0838884567",
                "It is dinner time!",
                3);

        message5 = new Message(
                "Developer",
                "+27838884567",
                "Ok, I am leaving without you.",
                4);

        message4.messageID = "0838884567";
        message4.messageHash = message4.createMessageHash();

        manager.addMessage(message1, Message.Status.SENT);
        manager.addMessage(message2, Message.Status.STORED);
        manager.addMessage(message3, Message.Status.DISREGARDED);
        manager.addMessage(message4, Message.Status.SENT);
        manager.addMessage(message5, Message.Status.STORED);
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {

        assertEquals(2, manager.getSentMessages().size());

        assertTrue(manager.getSentMessages().contains(message1));
        assertTrue(manager.getSentMessages().contains(message4));
    }

    @Test
    public void testDisregardedMessagesArrayCorrectlyPopulated() {

        assertEquals(1, manager.getDisregardedMessages().size());

        assertTrue(
                manager.getDisregardedMessages().contains(message3));
    }

    @Test
    public void testStoredMessagesArrayCorrectlyPopulated() {

        assertEquals(2, manager.getStoredMessages().size());

        assertTrue(manager.getStoredMessages().contains(message2));
        assertTrue(manager.getStoredMessages().contains(message5));
    }

    @Test
    public void testMessageHashArrayCorrectlyPopulated() {

        assertEquals(5, manager.getMessageHashes().size());

        assertTrue(manager.getMessageHashes().contains(
                message1.getMessageHash()));

        assertTrue(manager.getMessageHashes().contains(
                message5.getMessageHash()));
    }

    @Test
    public void testMessageIDArrayCorrectlyPopulated() {

        assertEquals(5, manager.getMessageIDs().size());

        assertTrue(manager.getMessageIDs().contains(
                message1.getMessageID()));

        assertTrue(manager.getMessageIDs().contains(
                message4.getMessageID()));
    }

    @Test
    public void testLongestStoredMessage() {

        Message longest = manager.getLongestStoredMessage();

        assertNotNull(longest);

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                longest.getMessageText());
    }

    @Test
    public void testSearchByMessageID() {

        Message found = manager.searchByMessageID("0838884567");

        assertNotNull(found);
        assertEquals("0838884567", found.getMessageID());
        assertEquals("It is dinner time!", found.getMessageText());
    }

    @Test
    public void testSearchByRecipient() {

        var results = manager.searchByRecipient("+27838884567");

        assertEquals(2, results.size());
    }

    @Test
    public void testDeleteStoredMessageUsingHash() {

        String hash = message2.getMessageHash();

        assertEquals(2, manager.getStoredMessages().size());

        boolean deleted = manager.deleteStoredMessageByHash(hash);

        assertTrue(deleted);
        assertEquals(1, manager.getStoredMessages().size());

        assertFalse(manager.getStoredMessages().contains(message2));
    }

    @Test
    public void testDisplayStoredMessageReport() {

        String report = manager.displayStoredMessageReport();

        assertTrue(report.contains(message2.getMessageHash()));
        assertTrue(report.contains(message2.getRecipient()));
        assertTrue(report.contains(message2.getMessageText()));

        assertTrue(report.contains(message5.getMessageHash()));
        assertTrue(report.contains(message5.getRecipient()));
        assertTrue(report.contains(message5.getMessageText()));
    }
}