package service;


import Controller.ChatroomController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class client {
    private Socket remoteSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public client(Socket remoteSocket,String clientUserName){
        this.remoteSocket=remoteSocket;
        this.clientUserName=clientUserName;
        try {
            this.bufferedReader=new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(remoteSocket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Can not create client");
            closeServer();
            throw new RuntimeException(e);
        }
        sendUserName(clientUserName);
    }

    public void closeServer(){
        try {
            if (remoteSocket != null){
                remoteSocket.close();
            }
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
        }catch (IOException e){
            System.out.println("closed client");
            throw new RuntimeException(e);
        }
    }

    public void sendUserName(String clientUserName){
        try {
            bufferedWriter.write(clientUserName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Can't sent Client Username");
            closeServer();
            throw new RuntimeException(e);
        }
    }

    public void sentMessage(String messageToSent) {
        try {
            bufferedWriter.write(messageToSent);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Error!!! Can't sent message to group");
            closeServer();
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage(VBox vbox_messages) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (remoteSocket.isConnected()){
                    try {
                        String  receiveMessage= bufferedReader.readLine();
                        ChatroomController.receiveMessage(receiveMessage,vbox_messages);
                    } catch (IOException e) {
                        System.out.println("Error!!! Can't read received message from client");
                        closeServer();
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }

}
