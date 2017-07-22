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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FILIP
 */
public class SOAzurirajKompaniju extends AbstractSO{

    AbstractObject kompanijaZaIzmjenu;

    public SOAzurirajKompaniju(AbstractObject kompanijaZaIzmjenu) {
        this.kompanijaZaIzmjenu = kompanijaZaIzmjenu;
    }
    
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        dbb.azurirajObjekat(kompanijaZaIzmjenu);
        Kompanija k = (Kompanija) kompanijaZaIzmjenu;
        for (Bransa bransa : k.getBranse()) {
            try {
                dbb.sacuvajUAgregirajucu(kompanijaZaIzmjenu, bransa, "kompanijabransa");
            } catch (SQLException ex) {
                Logger.getLogger(SOAzurirajKompaniju.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public AbstractObject getKompanijaZaIzmjenu() {
        return kompanijaZaIzmjenu;
    }
    
}
