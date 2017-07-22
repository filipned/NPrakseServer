/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Bransa;
import domen.Kompanija;
import exception.ServerException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FILIP
 */
public class SOUcitajSveKompanije extends AbstractSO {

    List<AbstractObject> kompanije;
    List<Bransa> branse = new ArrayList<>();
    List<Integer> listaKljuceva;
    Kompanija kompanija;

    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        kompanije = dbb.vratiSveObjekte(new Kompanija());
        
        for (AbstractObject k : kompanije) {
            System.out.println("KOmpanija: " + k);
            if(k instanceof Kompanija) {
                kompanija = (Kompanija) k;
                listaKljuceva = dbb.vratiObjektePoKljucu(new Kompanija(), "kompanijabransa", "bransaID", kompanija.getKompanijaID());
               
                for (Integer i : listaKljuceva) {
                    Bransa bransa = (Bransa) dbb.vratiObjekatPoKljucu(new Bransa(), i);
                    branse.add(bransa);
                }
                kompanija.setBranse(branse);
            }
            branse = new ArrayList<>();
        }
        if(kompanije == null) 
            throw new ServerException("Greska pri ucitavanju kompanija iz baze!");
    }

    public List<AbstractObject> getKompanije() {
        return kompanije;
    }
    
}
