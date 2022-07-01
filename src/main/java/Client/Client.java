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
        Runnable  receiveFromServer = () -> {
                        while (connection.isOpen()) {
                            String inputFromServer = connection.receive();
                            if (getMessageContent(new Message(inputFromServer)).equals("you won!")
                                        || getMessageContent(new Message(inputFromServer)).equals("you lost!")) {
                                connection.close();
                            }
                            System.out.println(getMessageContent(new Message(inputFromServer)));
                            synchronized (this) {
                                this.notify();
                            }
                        }};
        Runnable sendToServer = () -> {
                    while (connection.isOpen()) {
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
                };
        Thread thread1 = new Thread(receiveFromServer);
        Thread thread2 = new Thread(sendToServer);
        thread1.start();
        thread2.start();
        }

    private String getMessageContent(Message message){
        authToken=message.getAuthToken();
        needInput= message.needInput();
        return message.getContent();
    }

}
