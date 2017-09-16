/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import form.ServerForm;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
    
    public static volatile ServerSocket serverSocket;
    private static final int PORT = 9000;
    static List<ClientThread> klijenti = new ArrayList<>();
    static boolean radi = true;
    ServerForm serverForm;
    Socket socket;
    private static boolean running;

    public Server(ServerForm serverForm) {
        try {
            this.serverForm = serverForm;
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
        while(!isInterrupted()) {
            try {
                socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(klijenti, socket);
                clientThread.start();
                System.out.println("Novi klijent povezan!");
                klijenti.add(clientThread);
            
            }catch (SocketException ex) {
                
            }catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    public void zaustaviNiti() {
//        this.interrupt();
        try {
            
            serverSocket.close();
            
            for (ClientThread clientThread : klijenti) {
                clientThread.getSocket().close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
