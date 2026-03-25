package commands;

import transport.Request;
import transport.Response;

public abstract class Command {
    public abstract Response execute(Request request);
}
