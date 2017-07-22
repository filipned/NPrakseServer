/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Praksa;
import exception.ServerException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FILIP
 */
public class SOSacuvajPraksu extends AbstractSO {

    AbstractObject praksa;

    public SOSacuvajPraksu(AbstractObject praksa) {
        this.praksa = praksa;
    }

    public AbstractObject getPraksa() {
        return praksa;
    }
    
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        if(praksa == null)
            throw new ServerException("Greska!");
        try {
            List<AbstractObject> prakse = dbb.vratiSveObjekte(new Praksa());
            if(prakse.contains(praksa)) 
                throw new ServerException("Praksa vec postoji!");
            praksa = dbb.sacuvajObjekat(praksa);
            if(praksa == null)
                throw new ServerException("Neuspjesan unos!");
        } catch (SQLException ex) {
            Logger.getLogger(SOSacuvajPraksu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
