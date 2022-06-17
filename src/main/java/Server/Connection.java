package Server;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

    private final PrintStream out;
    private final Scanner in;

    public Connection(Socket socket) throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintStream(socket.getOutputStream());
    }

    public void send(String message) {
        out.println(message);
    }

    public void send(Object obj) {
        this.send(obj.toString());
    }

    /**
     *  returns a new message from server (if available),
     *  otherwise blocks
     */
    public String receive() {
        if (in.hasNextLine())
            return in.nextLine();
        return null;
    }
    /**
     *  @return true if there is new message
     *  @apiNote This method may block for input
     */
    public boolean hasNextLine() {
        return in.hasNextLine();
    }

    public void sendObject(Serializable object) {
        send(object.toString());
    }

    /**
     * closes connection.
     * notice this may be the only way to cancel the wait for nextLine or hasNextLine
     */
    public void close() {
        in.close();
        out.close();
    }


}
