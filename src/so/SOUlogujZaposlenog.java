/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import controler.Controler;
import domen.AbstractObject;
import domen.Zaposleni;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author FILIP
 */
public class SOUlogujZaposlenog extends AbstractSO {

    private AbstractObject zaposleni;

    public SOUlogujZaposlenog(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }
    
    /**
     *
     * @throws ServerException
     */
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        List<AbstractObject> sviZaposleni = dbb.vratiSveObjekte(new Zaposleni());
        if(!sviZaposleni.contains(zaposleni)) {
            throw new ServerException("Zaposleni ne postoji u bazi!");
        }
    }

    public AbstractObject getZaposleni() {
        return zaposleni;
    }

    
    
    
}
