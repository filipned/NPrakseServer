/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import exception.ServerException;

/**
 *
 * @author FILIP
 */
public class SOObrisiKompaniju extends AbstractSO {

    AbstractObject kompanijaZaBrisanje;

    public SOObrisiKompaniju(AbstractObject kompanijaZaBrisanje) {
        this.kompanijaZaBrisanje = kompanijaZaBrisanje;
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        dbb.obrisiObjekat(kompanijaZaBrisanje);
    }

    public AbstractObject getKompanijaZaBrisanje() {
        return kompanijaZaBrisanje;
    }
    
    
}
