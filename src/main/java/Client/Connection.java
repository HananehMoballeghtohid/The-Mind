package Client;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;

public class Connection {

    private final PrintWriter out;
    private final Scanner in;

    /**
     * constructor used by the clients
     *
     * @throws IOException
     */

    public Connection(Socket socket) throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
    }

    public void send(String message) {
        out.println(message);
        out.flush();
    }

    public void send(Object obj) {
        this.send(obj.toString());
    }

    /**
     *  returns a new message from server (if available),
     *  otherwise blocks
     */
    public String receive() {
        while (in.nextLine() == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
        return in.nextLine();
    }
    /**
     *  @return true if there is new message
     *  @apiNote This method may block for input
     */
    public boolean hasNextLine() {
        return in.hasNextLine();
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
