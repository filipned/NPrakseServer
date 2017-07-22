/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DatabaseBroker;
import exception.ServerException;


/**
 *
 * @author FILIP
 */
public abstract class AbstractSO {

    protected DatabaseBroker dbb;
    
    public AbstractSO() {
        dbb = new DatabaseBroker();
    }
    
    synchronized public void izvrsiOperaciju() throws ServerException {
        otvoriKonekciju();
        izvrsiKonkretnuOperaciju();
        potvrdiTransakciju();
        zatvoriKonekciju();
    }

    private void otvoriKonekciju() {
        dbb.uspostaviKonekciju();
    }


    private void potvrdiTransakciju() {
        dbb.potvrdiTransakciju();
    }

    private void zatvoriKonekciju() {
        dbb.raskiniKonekciju();
    }
    
    protected abstract void izvrsiKonkretnuOperaciju() throws exception.ServerException;

    
    
    
}
