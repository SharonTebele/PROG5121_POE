package src.main.java.com.PROG5121.POE_Part1;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static int messageNumber = 0;

    public static void main(String[] args) {

        Login user = new Login();

        System.out.println("========== REGISTRATION ==========");

        System.out.print("Enter first name: ");
        user.firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        user.lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        user.username = scanner.nextLine();

        System.out.print("Enter password: ");
        user.password = scanner.nextLine();

        System.out.print("Enter cell number (+27...): ");
        user.cellNumber = scanner.nextLine();

        System.out.println(user.registerUser());

        if (!user.checkUserName()
                || !user.checkPasswordComplexity()
                || !user.checkCellNumber()) {
            System.out.println("Registration failed.");
            return;
        }

        System.out.println("\n========== LOGIN ==========");

        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        System.out.println(
                user.returnLoginStatus(enteredUsername, enteredPassword)
        );

        if (!user.loginUser(enteredUsername, enteredPassword)) {
            return;
        }

        MessageManager manager = new MessageManager();
        manager.loadStoredMessages();

        boolean running = true;

        while (running) {

            System.out.println("\n========== QUICKCHAT MENU ==========");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");

            System.out.print("Choose an option: ");

            int option = readInteger();

            switch (option) {

                case 1:
                    sendMessages(manager, user);
                    break;

                case 2:
                    displaySentMessages(manager);
                    break;

                case 3:
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                case 4:
                    storedMessagesMenu(manager);
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    private static void sendMessages(
            MessageManager manager, Login user) {

        System.out.print("How many messages do you want to enter? ");
        int count = readInteger();

        if (count <= 0) {
            System.out.println("Enter a number greater than zero.");
            return;
        }

        int accepted = 0;

        while (accepted < count) {

            System.out.print("Enter recipient cell number (+27...): ");
            String recipient = scanner.nextLine();

            System.out.print("Enter message (maximum 250 characters): ");
            String text = scanner.nextLine();

            if (text.length() > 250 || text.isBlank()) {
                System.out.println("Invalid message length.");
                continue;
            }

            Message message = new Message(
                    user.firstName + " " + user.lastName,
                    recipient,
                    text,
                    messageNumber
            );

            if (message.checkRecipientCellTest() == 0) {
                System.out.println(message.checkRecipientCell());
                continue;
            }

            System.out.println("Message ID: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());

            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message");

            int choice = readInteger();

            switch (choice) {

                case 1:
                    manager.addMessage(message, Message.Status.SENT);
                    System.out.println("Message successfully sent.");
                    break;

                case 2:
                    manager.addMessage(message, Message.Status.DISREGARDED);
                    System.out.println("Message disregarded.");
                    break;

                case 3:
                    manager.addMessage(message, Message.Status.STORED);
                    System.out.println("Message successfully stored.");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            // Guideline 4, Step 6:
            accepted++;
            messageNumber++;
        }
    }

    private static void displaySentMessages(MessageManager manager) {

        if (manager.getSentMessages().isEmpty()) {
            System.out.println("There are no sent messages.");
            return;
        }

        for (Message message : manager.getSentMessages()) {
            System.out.println("Message ID: " + message.getMessageID());
            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("Message: " + message.getMessageText());
        }
    }

    private static void storedMessagesMenu(MessageManager manager) {

        boolean inMenu = true;

        while (inMenu) {

            System.out.println("\n========== STORED MESSAGES ==========");
            System.out.println("1. Display sender and recipient");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search by message ID");
            System.out.println("4. Search by recipient");
            System.out.println("5. Delete message using hash");
            System.out.println("6. Display full report");
            System.out.println("7. Return to main menu");

            int choice = readInteger();

            switch (choice) {

                case 1:
                    System.out.println(
                            manager.displayStoredSendersAndRecipients());
                    break;

                case 2:
                    Message longest = manager.getLongestStoredMessage();

                    if (longest == null) {
                        System.out.println("No stored messages.");
                    } else {
                        System.out.println(longest.getMessageText());
                    }
                    break;

                case 3:
                    System.out.print("Enter message ID: ");
                    Message found = manager.searchByMessageID(
                            scanner.nextLine());

                    if (found == null) {
                        System.out.println("Message not found.");
                    } else {
                        System.out.println("Recipient: " + found.getRecipient());
                        System.out.println("Message: " + found.getMessageText());
                    }
                    break;

                case 4:
                    System.out.print("Enter recipient number: ");

                    ArrayList<Message> results =
                            manager.searchByRecipient(scanner.nextLine());

                    for (Message message : results) {
                        System.out.println(message.getMessageText());
                    }

                    if (results.isEmpty()) {
                        System.out.println("No messages found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter message hash: ");

                    if (manager.deleteStoredMessageByHash(
                            scanner.nextLine())) {
                        System.out.println("Message deleted.");
                    } else {
                        System.out.println("Message not found.");
                    }
                    break;

                case 6:
                    System.out.println(
                            manager.displayStoredMessageReport());
                    break;

                case 7:
                    inMenu = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static int readInteger() {

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
