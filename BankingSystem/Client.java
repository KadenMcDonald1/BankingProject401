package BankingSystem;

import java.io.*;
import java.net.*;

public class Client {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // connect to server
    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);

            // IMPORTANT: output stream first, then input
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // send message and get response
    public Message sendMessage(Message msg) {
        try {
            out.writeObject(msg);   // serialize + send
            out.flush();

            Message response = (Message) in.readObject(); // receive + deserialize
            return response;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // close connection
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();

            System.out.println("Connection closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}