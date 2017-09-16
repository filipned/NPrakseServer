/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import controler.Controler;
import domen.AbstractObject;
import domen.Zaposleni;
import exception.ServerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.Operations;
import transfer.ClientRequest;
import transfer.ServerResponse;

/**
 *
 * @author FILIP
 */
class ClientThread extends Thread {

    List<ClientThread> klijenti;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    AbstractObject zaposleni;
            
    ClientThread(List<ClientThread> klijenti, Socket socket) {
        this.klijenti = klijenti;
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Ulazni i izlazni strimovi uspjesno uspostavljeni!");
        } catch (IOException ex) {
            System.out.println("Greska pri uspostavljanju ulaznog i izlaznog strima!");
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        
        while (true) {            
            try {
                System.out.println("Ceknnje na zahtjev od strane klijenta...");
                ClientRequest clr = (ClientRequest) in.readObject();
                System.out.println("Primljen zahtjev od klijenta!");
                ServerResponse ser = new ServerResponse();
                
                try{
                    switch(clr.getOperacija()) {
                        case Operations.ULOGUJ_ZAPOSLENOG:
                            zaposleni = (Zaposleni) Controler.getInstance().ulogujZaposlenog((Zaposleni)clr.getObject());
                            ser.setPodaci(zaposleni);
                            break;
                        case Operations.UCITAJ_SVE_FAKULTETE:
                            List<AbstractObject> fakulteti = (List<AbstractObject>) Controler.getInstance().ucitajSveFakultete();
                            ser.setPodaci(fakulteti);
                            break;
                        case Operations.SACUVAJ_STUDENTA:
                            AbstractObject student = Controler.getInstance().sacuvajStudenta((AbstractObject)clr.getObject());
                            ser.setPodaci(student);
                            break;
                        case Operations.UCITAJ_SVE_BRANSE:
                            List<AbstractObject> branse = (List<AbstractObject>)Controler.getInstance().ucitajSveBranse();
                            ser.setPodaci(branse);
                            break;
                        case Operations.SACUVAJ_KOMPANIJU:
                            AbstractObject kompanija = Controler.getInstance().sacuvajKompaniju((AbstractObject)clr.getObject());
                            ser.setPodaci(kompanija);
                            break;
                        case Operations.UCITAJ_SVE_STUDENTE:
                            List<AbstractObject> studenti = (List<AbstractObject>)Controler.getInstance().ucitajSveStudente();
                            ser.setPodaci(studenti);
                            break;
                        case Operations.UCITAJ_SVE_KOMPANIJE:
                            List<AbstractObject> kompanije = (List<AbstractObject>)Controler.getInstance().ucitajSveKompanije();
                            ser.setPodaci(kompanije);
                            break;
                        case Operations.OBRISI_STUDENTA:
                            AbstractObject studentZaBrisanje = Controler.getInstance().obrisiStudenta((AbstractObject)clr.getObject());
                            ser.setPodaci(studentZaBrisanje);
                            break;
                        case Operations.IZMENI_STUDENTA:
                            AbstractObject studentZaIzmjenu = Controler.getInstance().azurirajStudenta((AbstractObject)clr.getObject());
                            ser.setPodaci(studentZaIzmjenu);
                            break;
                        case Operations.OBRISI_KOMPANIJU:
                            AbstractObject kompanijaZaBrisanje = Controler.getInstance().obrisiKompaniju((AbstractObject)clr.getObject());
                            ser.setPodaci(kompanijaZaBrisanje);
                            break;
                        case Operations.IZMENI_KOMPANIJU:
                            AbstractObject kompanijaZaIzmjenu = Controler.getInstance().azurirajKompaniju((AbstractObject)clr.getObject());
                            ser.setPodaci(kompanijaZaIzmjenu);
                            break;
                        case Operations.SACUVAJ_PRAKSU:
                            AbstractObject praksa = Controler.getInstance().sacuvajPraksu((AbstractObject) clr.getObject());
                            ser.setPodaci(praksa);
                            break;
                        case Operations.UCITAJ_SVE_PRAKSE:
                            List<AbstractObject> prakse = (List<AbstractObject>)Controler.getInstance().ucitajSvePrakse();
                            ser.setPodaci(prakse);
                            break;
                        default:
                                ser.getPodaci();
                                break;
                        
                    }
                    ser.setUspesnost(Operations.USPESNO);
                    
                } catch(ServerException ex) {
                    ser.setUspesnost(-1);
                    ser.setException(ex);
                }
                out.writeObject(ser);
                System.out.println(ser.getUspesnost());
                
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    
    
    
    
}
