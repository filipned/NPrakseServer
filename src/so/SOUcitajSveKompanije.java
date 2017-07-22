/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Kompanija;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author FILIP
 */
public class SOUcitajSveKompanije extends AbstractSO {

    List<AbstractObject> kompanije;
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        kompanije = dbb.vratiSveObjekte(new Kompanija());
        
        if(kompanije == null || kompanije.isEmpty()) 
            throw new ServerException("Greska pri ucitavanju kompanija iz baze!");
    }

    public List<AbstractObject> getKompanije() {
        return kompanije;
    }
    
}
