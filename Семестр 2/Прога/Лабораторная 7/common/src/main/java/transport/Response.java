package transport;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private List<String> messages;
    private boolean state;

    public Response(List<String> messages, boolean state) {
        this.messages = messages;
        this.state = state;
    }

    public Response(List<String> messages) {
        this.messages = messages;
    }

    public Response(boolean state) {
        this.state = state;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void printMessages() {
        for (String message : this.messages) {
            System.out.println(message);
        }
    }

    public boolean getState() {
        return this.state;
    }
}
