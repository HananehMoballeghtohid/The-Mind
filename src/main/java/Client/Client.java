package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String authToken;
    private boolean needInput;

    public void init() throws IOException {
        Socket socket = new Socket("localhost",8000);
        Connection connection = new Connection(socket);
        Scanner console = new Scanner(System.in);
        new Thread(
                    ()->{
                        while (true) {
                            String inputFromServer = connection.receive();
                            if (inputFromServer.equals("you won!") || inputFromServer.equals("you lost!")) {
                                break;
                            }
                            System.out.println(getMessageContent(new Message(inputFromServer)));
                            synchronized (this) {
                                this.notify();
                            }
                        }}
            ).start();
            new Thread(
                    ()-> {
                        while (true) {
                            synchronized (this) {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (needInput ) {
                                String input = console.nextLine();
                                connection.send(new Message(input, authToken, "0"));
                                needInput=false;
                            }
                        }
                    }
            ).start();
        }

    private String getMessageContent(Message message){
        authToken=message.getAuthToken();
        needInput= message.needInput();
        return message.getContent();
    }
}
