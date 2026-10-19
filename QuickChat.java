// PART 2 STARTS - Only after successful login
System.out.println("\nWelcome to QuickChat.");
Message.loadMessages(); // loads JSON if it exists

boolean running = true;
while (running) {
    System.out.println("\n--- QuickChat Menu ---");
    System.out.println("1) Send Messages");
    System.out.println("2) Show recently sent messages");
    System.out.println("3) Quit");
    System.out.print("Choose option: ");
    
    int option = 0;
    try {
        option = Integer.parseInt(sc.nextLine());
    } catch (Exception e) {
        System.out.println("Please enter a number 1-3");
        continue;
    }

    switch (option) {
        case 1: // SEND MESSAGES
            System.out.print("How many messages do you wish to enter? ");
            int howMany = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < howMany; i++) {
                System.out.println("\n--- Message " + (i+1) + "/" + howMany + " ---");

                // 1. Generate 10-digit random ID
                String messageID = String.format("%010d", new java.util.Random().nextInt(1000000000));
                // Alternative that always gives 10 digits:
                // long id = (long)(Math.random() * 9000000000L) + 1000000000L;
                // String messageID = String.valueOf(id);

                // 2. Recipient input + validation
                System.out.print("Enter Recipient Cell (+27...): ");
                String recipient = sc.nextLine();
                
                Message msgCheck = new Message();
                msgCheck.recipient = recipient;
                msgCheck.messageID = messageID;
                
                if (msgCheck.checkRecipientCellTest() == 0) {
                    System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                    i--; // repeat this message
                    continue;
                } else {
                    System.out.println("Cell phone number successfully captured.");
                }

                // 3. Message input + 250 char check
                System.out.print("Enter your message (max 250 chars): ");
                String text = sc.nextLine();
                
                if (text.length() > 250) {
                    System.out.println("Please enter a message of less than 250 characters. You entered: " + text.length());
                    i--; // repeat this message
                    continue;
                }

                // 4. Create Message object - this creates hash automatically
                Message msg = new Message(recipient, text, Message.messagesSent);
                msg.messageID = messageID; // use our generated ID
                String hash = msg.createMessageHash();

                // 5. Show details (what lecturer wants to see)
                System.out.println("MessageID: " + msg.messageID);
                System.out.println("Message Hash: " + hash);
                System.out.println("Recipient: " + msg.recipient);
                System.out.println("Message: " + msg.messageText);

                // 6. Ask Send / Disregard / Store
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Send Message");
                System.out.println("2) Disregard Message - Press 0 to delete");
                System.out.println("3) Store Message to send later");
                System.out.print("Choice: ");
                int sendChoice = Integer.parseInt(sc.nextLine());
                
                System.out.println(msg.SentMessage(sendChoice));

                if (sendChoice == 1) { // Send
                    msg.storeMessage(); // also stores to JSON for records
                    Message.messagesSent++;
                } else if (sendChoice == 2) { // Disregard
                    System.out.println("Message deleted. Press 0 to confirm - skipping store.");
                } else if (sendChoice == 3) { // Store
                    msg.storeMessage();
                    Message.messagesSent++;
                }
                System.out.println("Total Messages Sent/Stored: " + Message.totalMessages);
            }
            break;

        case 2:
            System.out.println("Coming Soon.");
            // For extra marks you can uncomment:
            // System.out.println(new Message().printMessages());
            break;

        case 3:
            running = false;
            System.out.println("Total messages sent: " + Message.messagesSent);
            System.out.println("Quitting QuickChat. Bye!");
            break;

        default:
            System.out.println("Invalid option. Try 1, 2 or 3.");
    }
}