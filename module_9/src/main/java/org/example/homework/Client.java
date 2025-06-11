package org.example.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    private static final int TIME_OF_ACTIVITY = 30;
    private static Timer timer;

    //Тут все также как и для сервера по таймеру
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 8087;

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connecting...");
            startTimer(socket);
            String clientMessage;
            String serverMessage;
            String login;
            String password;
            int counter = 0;
            while (true) {
                if (counter == 0) {
                    System.out.println("Client: Enter login: ");
                    login = consoleInput.readLine();
                    System.out.println("Client: Enter password: ");
                    password = consoleInput.readLine();
                    out.println(login + " " + password);
                } else {
                    System.out.println("Client: ");
                    clientMessage = consoleInput.readLine();
                    resetTimer(socket);
                    out.println(clientMessage);

                    if (clientMessage.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected: " + socket.getInetAddress());
                        break;
                    }
                }
                serverMessage = in.readLine();
                if (Objects.isNull(serverMessage) || serverMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Connection is closing...");
                    break;
                }
                if (!serverMessage.equalsIgnoreCase("wrong")) {
                    counter++;
                }
                resetTimer(socket);
                System.out.println("Server: " + serverMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startTimer(Socket clientSocket) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("No activity within " + TIME_OF_ACTIVITY + ". Connection is closing...");
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }, TIME_OF_ACTIVITY * 1000);
    }

    private static void resetTimer(Socket clientSocket) {
        timer.cancel();
        startTimer(clientSocket);
    }
}
