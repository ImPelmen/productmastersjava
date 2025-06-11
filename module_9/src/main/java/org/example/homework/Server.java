package org.example.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    private static final int TIME_OF_ACTIVITY = 30;
    private static Timer timer;

    private static HashMap<String, String> authMap = new HashMap<>();

    public static void main(String[] args) {
        int port = 8087;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is active now...");
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
            ) {
                authMap.put("test", "password");
                System.out.println("Client is connected: " + clientSocket.getInetAddress());
                //Стартуем таймер
                startTimer(clientSocket);
                String clientMessage;
                String serverMessage;
                int counter = 0;
                while (true) {
                    if (in.ready()) {
                        clientMessage = in.readLine();
                        if (Objects.isNull(clientMessage) || clientMessage.equalsIgnoreCase("exit")) {
                            System.out.println("Client is disconnected: " + clientSocket.getInetAddress());
                            break;
                        }
                        //Тут ресетаем для клиента
                        resetTimer(clientSocket);
                        if (clientMessage.equalsIgnoreCase("/time")) {
                            out.println(LocalDateTime.now());
                        } else if (counter == 0) {
                            String[] authClient = clientMessage.split(" ");
                            if (Objects.isNull(authClient[0])) {
                                System.out.println("Login can not be null or empty...");
                            }
                            if (Objects.isNull(authClient[1])) {
                                System.out.println("Password can not be null or empty...");
                            }
                            if (authMap.containsKey(authClient[0]) && authMap.get(authClient[0]).equals(authClient[1])) {
                                out.println("Welcome, " + authClient[0]);
                                counter++;
                            } else {
                                out.println("wrong");
                            }

                        }else {
                            System.out.println("Client: " + clientMessage);
                            System.out.println("Server: ");
                            serverMessage = consoleInput.readLine();
                            //Тут ресетаем, потому что сервер
                            resetTimer(clientSocket);
                            out.println(serverMessage);
                            if (serverMessage.equalsIgnoreCase("exit")) {
                                System.out.println("Connection is closing...");
                                break;
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
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
