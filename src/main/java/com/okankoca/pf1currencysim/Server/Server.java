package com.okankoca.pf1currencysim.Server;

import com.okankoca.pf1currencysim.Simulation.RateSimulatorForPF1;
import com.okankoca.pf1currencysim.Simulation.RatesInitialization;

import java.net.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        System.out.println("Server started");
        while (!serverSocket.isClosed()) {
            try{
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                RatesInitialization ratesInitialization = new RatesInitialization();

                ClientHandler clientHandler = new ClientHandler(socket, ratesInitialization);

                Thread thread = new Thread(clientHandler);
                thread.start();

                RateSimulatorForPF1 rateSimulator = new RateSimulatorForPF1(ratesInitialization);

                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                scheduler.scheduleAtFixedRate(() -> {
                    rateSimulator.simulateRates(); // Simüle kurları güncelle
                    System.out.println("Rates updated: " + ratesInitialization.getAllRates());
                }, 0, 5, TimeUnit.SECONDS); // 1 saniyede bir kurları güncelle


            }catch (IOException e){
                System.out.println(e);
            }
        }
    }

    public void closeServer() {
        if (serverSocket != null) {
            try{
                serverSocket.close();
                System.out.println("Server closed");
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(8081);
            Server server = new Server(serverSocket);
            server.startServer();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
