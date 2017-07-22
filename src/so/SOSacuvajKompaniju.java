/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Bransa;
import domen.Kompanija;
import domen.Student;
import exception.ServerException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FILIP
 */
public class SOSacuvajKompaniju extends AbstractSO{
    
    AbstractObject kompanija;
    
    public SOSacuvajKompaniju(AbstractObject kompanija) {
        this.kompanija = kompanija;
    }

    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        if(kompanija == null)
            throw new ServerException("Greska!");
        try {
            List<AbstractObject> kompanije = dbb.vratiSveObjekte(new Kompanija());
            if(kompanije.contains(kompanija)) 
                throw new ServerException("Kompanija vec postoji!");
            kompanija = dbb.sacuvajObjekat(kompanija);
            if(kompanija == null)
                throw new ServerException("Neuspjesan unos!");
            Kompanija k = (Kompanija) kompanija;
        for (Bransa bransa : k.getBranse()) {
            dbb.sacuvajUAgregirajucu(kompanija, bransa, "kompanijabransa");
        }
        } catch (SQLException ex) {
            Logger.getLogger(SOSacuvajKompaniju.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public AbstractObject getKompanija() {
        return kompanija;
    }       
}
