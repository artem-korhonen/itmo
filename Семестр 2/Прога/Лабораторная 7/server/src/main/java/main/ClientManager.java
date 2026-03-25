package main;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
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
    private final ForkJoinPool readPool = new ForkJoinPool();
    private final ExecutorService processPool = Executors.newFixedThreadPool(8);
    private final ExecutorService sendPool = Executors.newFixedThreadPool(8);

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
                    readPool.execute(() -> {
                        try {
                            Request request = (Request) this.deserialize(bufferRequest);
                            processPool.execute(() -> {
                                try {
                                    Response response = this.commandManager.executeCommand(request);
                                    sendPool.execute(() -> {
                                        try {
                                            ByteBuffer bufferResponse = ByteBuffer.wrap(this.serialize(response));
                                            channel.send(bufferResponse, requestAddress);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                Thread.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readPool.shutdown();
            processPool.shutdown();
            sendPool.shutdown();
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