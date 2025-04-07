package com.okankoca.pf1currencysim.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8081)) {
            // Okuma ve yazma akışları
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Sunucudan gelen mesajları dinlemek için bir thread
            Thread listenerThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    System.err.println("Connection closed: " + e.getMessage());
                }
            });
            listenerThread.start();

            // Kullanıcıdan veri alarak sunucuya göndermek
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine();

                // "EXIT" girildiğinde bağlantıyı kes
                if (command.equalsIgnoreCase("EXIT")) {
                    break;
                }

                // Mesajı sunucuya gönder
                out.write(command);
                out.newLine();
                out.flush();
            }

            // Temizlik (bağlantıyı kapatma)
            socket.close();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}
