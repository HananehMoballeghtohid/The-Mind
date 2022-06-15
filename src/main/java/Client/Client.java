package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public void init() throws IOException {
        Socket socket = new Socket("localhost",8000);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());
        Scanner console = new Scanner(System.in);
        while (true){
            System.out.println(in.nextLine());
            String input = console.nextLine();
            out.println(input);
            out.flush();
        }
    }
}
