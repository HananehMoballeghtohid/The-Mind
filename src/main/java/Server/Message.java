package Server;

import java.io.Serializable;

public class Message implements Serializable {

    private final String content;
    private final String authToken;

    public Message(String content, String authToken) {
        this.content = content;
        this.authToken = authToken;
    }

    public String getContent() {
        return content;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return content;
    }

}
