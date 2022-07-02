package Server;

import java.io.Serializable;

public class Message implements Serializable {

    private final String content;
    private final String authToken;
    private final String needInput;

    public Message(String content, String authToken ,String needInput) {
        this.content = content;
        this.authToken = authToken;
        this.needInput=needInput;
    }

    public Message(String message){
        String[] contentToken = message.split("/-/");
        content=contentToken[0];
        authToken=contentToken[1];
        needInput=contentToken[2];
    }

    public String getContent() {
        return content;
    }

    public String getAuthToken() {
        return authToken;
    }

    public boolean needInput(){
        return "1".equals(needInput);
    }

    @Override
    public String toString() {
        return content +"/-/"+authToken +"/-/" + needInput;
    }

}
