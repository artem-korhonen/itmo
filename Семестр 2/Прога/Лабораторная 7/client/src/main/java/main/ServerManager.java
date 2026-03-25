package main;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

import data.City;
import transport.Request;
import transport.Response;
import user.Profile;


public class ServerManager {
    String host;
    Integer port;

    public ServerManager(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public Response sendCommand(String commandName, Number number, City city, Profile profile) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.socket().setSoTimeout(3000);
            InetSocketAddress serverAddress = new InetSocketAddress(this.host, this.port);

            Request request = new Request(commandName, number, city, profile);
            ByteBuffer bufferRequest = ByteBuffer.wrap(serialize(request));
            channel.send(bufferRequest, serverAddress);

            ByteBuffer bufferResponse = ByteBuffer.allocate(65536);
            long startTime = System.currentTimeMillis();

            while (true) {
                bufferResponse.clear();
                InetSocketAddress responseAddress = (InetSocketAddress) channel.receive(bufferResponse);

                if (responseAddress != null) {
                    Response response = (Response) deserialize(bufferResponse.flip());
                    return response;
                }

                if (System.currentTimeMillis() - startTime > 3000) {
                    System.out.println("Error: Timeout waiting for server response");
                    System.out.println("Error: Server is temporarily unavailable");
                    return null;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Failed to communicate with server");
            return null;
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
}