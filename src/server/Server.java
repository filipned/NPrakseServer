/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FILIP
 */
public class Server extends Thread {
    
    private ServerSocket serverSocket;
    private static final int PORT = 9000;
    static List<ClientThread> klijenti = new ArrayList<>();

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Kreiran server soket na portu " + PORT);
        } catch (IOException ex) {
            System.out.println("Neuspjesno kreiranje soketa na portu " + PORT);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        System.out.println("Cekanje na klijenta...");
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(klijenti, socket);
                clientThread.start();
                System.out.println("Novi klijent povezan!");
                klijenti.add(clientThread);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
}
