/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Fakultet;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author FILIP
 */
public class SOUcitajSveFakultete extends AbstractSO {

    List<AbstractObject> fakulteti;
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        fakulteti = dbb.vratiSveObjekte(new Fakultet());
        if(fakulteti == null || fakulteti.isEmpty()) 
            throw new ServerException("Greska pri ucitavanju fakulteta iz baze!");
    }

    public List<AbstractObject> getFakulteti() {
        return fakulteti;
    }

    
}
