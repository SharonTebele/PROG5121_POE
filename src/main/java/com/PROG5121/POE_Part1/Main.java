package src.main.java.com.PROG5121.POE_Part1;
**
 * Handles user registration and login validation
 * Author: Sharone Tebele
 * Attribution for Regex: https://www.geeksforgeeks.org/regex-validation-south-african-phone-number/
 */
import java.util.Scanner;

public class main {
     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login user = new Login();

        System.out.println("=== REGISTRATION ===");
        System.out.print("Enter First Name: ");
        user.firstName = sc.nextLine();
        System.out.print("Enter Last Name: ");
        user.lastName = sc.nextLine();
        System.out.print("Enter Username (must contain _ and <=5 chars): ");
        user.username = sc.nextLine();
        System.out.print("Enter Password (8 chars, Capital, Number, Special): ");
        user.password = sc.nextLine();
        System.out.print("Enter Cell Number (e.g. +27838970000): ");
        user.cellNumber = sc.nextLine();

        System.out.println(user.registerUser());
        if (!user.checkUserName() || !user.checkPasswordComplexity() || !user.checkCellPhoneNumber()) {
            System.out.println("Registration failed. Restart app.");
            return;
        }

        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter Username: ");
        String LoginU = sc.nextLine();
        System.out.print("Enter Password: ");
        String LoginP = sc.nextLine();
        System.out.println(user.returnLoginStatus(LoginU, LoginP));

        if (!user.loginUser(LoginU, LoginP)) return;

        // PART 2 STARTS
        System.out.println("\nWelcome to QuickChat.");
        Message.loadMessages();
        boolean running = true;
        while (running) {
            System.out.println("\nChoose: 1) Send Messages  2) Show recently sent messages  3) Quit");
            System.out.print("Option: ");
            int option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    System.out.print("How many messages you wish to enter? ");
                    int count = Integer.parseInt(sc.nextLine());
                    for (int i = 0; i < count; i++) {
                        System.out.println("\n--- Message " + (i+1) + " ---");
                        System.out.print("Enter Recipient (+27...): ");
                        String recipient = sc.nextLine();
                        System.out.print("Enter Message (max 250 chars): ");
                        String text = sc.nextLine();

                        if (text.length() > 250) {
                            System.out.println("Please enter a message of less than 250 characters");
                            i--; continue;
                        }

                        Message msg = new Message(recipient, text, Message.messagesSent);
                        System.out.println(msg.checkRecipientCell());
                        if (msg.checkRecipientCellTest() == 0) {
                            i--; continue;
                        }

                        msg.createMessageHash();
                        System.out.println("MessageID: " + msg.messageID);
                        System.out.println("Message Hash: " + msg.messageHash);
                        
                        System.out.println("Choose: 1) Send  2) Disregard  3) Store");
                        int sendChoice = Integer.parseInt(sc.nextLine());
                        System.out.println(msg.SentMessage(sendChoice));

                        if (sendChoice == 1 || sendChoice == 3) {
                            msg.storeMessage();
                            Message.messagesSent++;
                            System.out.println("Total Messages: " + Message.totalMessages);
                        }
                    }
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    running = false;
                    System.out.println("Total messages sent: " + Message.totalMessages);
                    System.out.println("Quitting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
    
}
