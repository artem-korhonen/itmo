package transport;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private List<String> messages;

    public Response(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void printMessages() {
        for (String message : this.messages) {
            System.out.println(message);
        }
    }
}
