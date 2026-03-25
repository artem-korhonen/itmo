package main;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import transport.Request;
import transport.Response;


public class ClientManager {
    private Integer port;
    private CommandManager commandManager;
    private boolean active = true;

    public ClientManager(Integer port, CommandManager commandManager) {
        this.port = port;
        this.commandManager = commandManager;
    }

    public void start() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(this.port));
            channel.configureBlocking(false);
            ByteBuffer bufferRequest = ByteBuffer.allocate(65536);

            while (this.active) {
                bufferRequest.clear();
                InetSocketAddress requestAddress = (InetSocketAddress) channel.receive(bufferRequest);

                if (requestAddress != null) {
                    Request request = (Request) this.deserialize(bufferRequest.flip());
                    Response response = new Response(this.commandManager.executeCommand(request.getCommandName(), request.getNumber(), request.getCity()));
                    ByteBuffer bufferResponse = ByteBuffer.wrap(this.serialize(response));
                    channel.send(bufferResponse, requestAddress);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            
        }
    }

    public byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(obj);
            return byteStream.toByteArray();
        }
    }

    public Object deserialize(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            return objectStream.readObject();
        }
    }

    public void breakClientManager() {
        this.active = false;
    }
}