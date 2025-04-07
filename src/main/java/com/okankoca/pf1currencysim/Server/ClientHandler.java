package com.okankoca.pf1currencysim.Server;

import com.okankoca.pf1currencysim.Simulation.RatesInitialization;
import com.okankoca.pf2currencysim.model.Rate;


import java.net.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private RatesInitialization ratesInitialization;
    private List<Rate> subscribedRates;
    private ScheduledExecutorService scheduler;


    public ClientHandler(Socket socket, RatesInitialization ratesInitialization){
        try{
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.ratesInitialization = ratesInitialization;
            this.subscribedRates = new ArrayList<>();
            this.scheduler = Executors.newScheduledThreadPool(1);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        String clientRequest;

        while(socket.isConnected()){
            try{
                clientRequest = in.readLine();
                handleClientMessage(clientRequest);
            }catch (Exception e){
                //e.printStackTrace(); // client bağlanıp disconnect olunca nullpointerexception fırlatıyor.
            }
        }
        stopSendingRates();
    }

    private void subscribe(String rateName){
        ratesInitialization.getAllRates().forEach(rate -> {
            if (rate.getRateName().equals(rateName)){
                if (!subscribedRates.isEmpty()){
                    subscribedRates.forEach(subRate -> {
                        if (subRate.getRateName().equalsIgnoreCase(rateName)){
                            out.println("already subscribed to " + rate.getRateName());
                        }
                        else{
                            this.subscribedRates.add(rate);
                            out.println("subscribed successfully to " + rate.getRateName());
                        }
                    });
                }
                else{
                    this.subscribedRates.add(rate);
                    out.println("subscribed successfully to " + rate.getRateName());
                }

                startSendingRates();
            }
//            else {
//                out.println("Error unknown rate");
//            }
        });
    }

    private void unsubscribe(String rateName){
        subscribedRates.forEach(rate -> {
            if (rate.getRateName().equals(rateName)){
                this.subscribedRates.remove(rate);
                out.println("unsubscribed successfully from " + rate.getRateName());
            }
//            else {
//                out.println("Error unknown rate"); // foreachte break yapamadığımız için hepsiyle karşılaştırıyor ve unsub olsa da diğerlerinde bunu ekrana yazıyor.
//            }
        });
    }

    private void sendRates(){
        this.subscribedRates.forEach(subRate -> {
            ratesInitialization.getAllRates().forEach(rate -> {
                if(rate.getRateName().equals(subRate.getRateName())){
                    out.println(rate.getRateName() + " " + rate.getBid() + " " + rate.getAsk());
                }
            });
        });
    }

    private void startSendingRates(){
        scheduler.scheduleAtFixedRate(()->{
          if(!this.subscribedRates.isEmpty()){
              sendRates();
          }
        },0,5, TimeUnit.SECONDS);
    }

    private void stopSendingRates(){
        if(scheduler != null && !scheduler.isShutdown()) scheduler.shutdownNow();
    }

    private void handleClientMessage(String clientRequest){
        ratesInitialization.getAllRates().forEach(rate -> {
            if (clientRequest.equalsIgnoreCase("subscribe" + " " + rate.getRateName())){
                subscribe(rate.getRateName());
            }
            else if (clientRequest.equalsIgnoreCase("unsubscribe" + " " + rate.getRateName())){
                unsubscribe(rate.getRateName());
            }
//            else {
//                System.out.println("Error unknown request");
//            }
        });

    }
}
