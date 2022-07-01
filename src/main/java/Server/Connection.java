package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

    private final PrintWriter out;
    private final Scanner in;
    private boolean open;
    /**
     * constructor used by the server
     */

    public Connection(Socket socket) throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
        open=true;
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
        return in.nextLine();
    }

    public boolean isOpen(){
        return open;
    }

    /**
     * closes connection.
     * notice this may be the only way to cancel the wait for nextLine or hasNextLine
     */
    public void close() {
        in.close();
        out.close();
        open=false;
    }
}
