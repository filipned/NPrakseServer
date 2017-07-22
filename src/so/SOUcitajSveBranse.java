/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Bransa;
import exception.ServerException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FILIP
 */
public class SOUcitajSveBranse extends AbstractSO {

    List<AbstractObject> branse = new ArrayList<>();
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        branse = dbb.vratiSveObjekte(new Bransa());
        if(branse == null || branse.isEmpty()) 
            throw new ServerException("Greska pri ucitavanju bransi iz baze!");
    }

    public List<AbstractObject> getBranse() {
        return branse;
    }
    
    
    
}
