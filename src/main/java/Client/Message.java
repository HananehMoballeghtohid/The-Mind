package Client;

import java.io.Serializable;

public class Message implements Serializable {

    private final String content;
    private final String authToken;

    public Message(String content, String authToken) {
        this.content = content;
        this.authToken = authToken;
    }

    public Message(String message){
        String[] contentToken = message.split("/-/");
        content=contentToken[0];
        authToken=contentToken[1];
    }

    public String getContent() {
        return content;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return content +"/-/"+authToken;
    }

}
