package model;

/**
 * A message for chat or log displayed in the game client
 *
 * Created by kcwillmore on 9/30/16.
 */
public class Message {
    private String message;
    private String source;

    public Message(String message, String source) {
        this.message = message;
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public String getSource() {
        return source;
    }

    //maybe a toString?
}
