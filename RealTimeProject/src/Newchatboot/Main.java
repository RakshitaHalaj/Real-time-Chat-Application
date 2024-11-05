package Newchatboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class ChatRoom {
    private String id;
    private List<String> messages;
    private List<String> activeUsers;
    //String name;

    public ChatRoom(String id) {
        this.id = id;
        this.messages = new ArrayList<>();
        this.activeUsers = new ArrayList<>();
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void addUser(String user) {
        activeUsers.add(user);
    }

    public void removeUser(String user) {
        activeUsers.remove(user);
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<String> getActiveUsers() {
        return activeUsers;
    }
   
  
    }


class ChatRoomManager {
    private static ChatRoomManager instance;
    private Map<String, ChatRoom> chatRooms;

    private ChatRoomManager() {
        chatRooms = new HashMap<>();
    }

    public static ChatRoomManager getInstance() {
        if (instance == null) {
            instance = new ChatRoomManager();
        }
        return instance;
    }

    public void createChatRoom(String id) {
        chatRooms.put(id, new ChatRoom(id));
    }

    public ChatRoom getChatRoom(String id) {
        return chatRooms.get(id);
    }
}

class User {
    String name;
    private ChatRoom chatRoom;

    public User(String name) {
        this.name = name;
    }

    public void joinChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.addUser(name);
    }

    public void sendMessage(String message) {
        chatRoom.addMessage(name + ": " + message);
    }

    public void leaveChatRoom() {
        chatRoom.removeUser(name);
    }
}

interface Observer {
    void update(String message);
}

class ChatRoomObserver implements Observer {
    private ChatRoom chatRoom;

    public ChatRoomObserver(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public void update(String message) {
        System.out.println("New message: " + message);
        System.out.println("Active users: " + chatRoom.getActiveUsers());
        
    }
}



public class Main {
	public static void main(String[] args) {
	 
		ChatRoomManager chatRoomManager = ChatRoomManager.getInstance();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the Chat Room ID:");
        String roomId = scanner.nextLine();

        chatRoomManager.createChatRoom(roomId);

        ChatRoom chatRoom = chatRoomManager.getChatRoom(roomId);

       
        System.out.println("Enter your name (only Alice, Bob, or Charlie are allowed):");
        String userName = scanner.nextLine();

        if (!userName.equals("Alice") && !userName.equals("Bob") && !userName.equals("Charlie")) {
            System.out.println("Only Alice, Bob, and Charlie are allowed to join the chat room.");
            System.exit(0);
        }

        
        //System.out.println("Available active users"+)
        

        //System.out.println("Enter your name:");
        //String userName = scanner.nextLine();

        User user = new User(userName);
        user.joinChatRoom(chatRoom);
        System.out.println("Active user: " + user.name);

        ChatRoomObserver observer = new ChatRoomObserver(chatRoom);
       

        while (true) {
        	System.out.println("Choose one given below option");
            System.out.println("1. Send message");
            System.out.println("2. Leave chat room");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            //Hello, everyone!,, "How's it going?", "Goodbye!"

            switch (choice) {
                case 1:
                    System.out.println("Enter your message:");
                    String message = scanner.nextLine();
                    if (!message.equals("Hello, everyone!") && !message.equals("How's it going?") && !message.equals("Goodbye!")) {
                        System.out.println("Only Hello, everyone!, \"How's it going?\", \"Goodbye!\" message are  allowed to join the chat room.");
                        System.exit(0);
                    }
                    user.sendMessage(message);
                    observer.update(user.name + ": " + message);
                    break;
                case 2:
                    user.leaveChatRoom();
                    System.out.println("You have left the chat room.");
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

}

