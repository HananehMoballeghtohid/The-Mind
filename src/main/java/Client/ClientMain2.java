package Client;

import java.io.IOException;

public class ClientMain2 {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
