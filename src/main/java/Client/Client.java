package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String authToken;

    public void init() throws IOException {
        Socket socket = new Socket("localhost",8000);
        Connection connection = new Connection(socket);
        Scanner console = new Scanner(System.in);
        while (true){
            String inputFromServer = connection.receive();
            System.out.println(getMessageContent(new Message(inputFromServer)));
            String input = console.nextLine();
            connection.send(new Message(input,authToken));
        }
    }

    private String getMessageContent(Message message){
        authToken=message.getAuthToken();
        return message.getContent();
    }
}
