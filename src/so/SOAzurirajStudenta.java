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
public class SOAzurirajStudenta extends AbstractSO {

    AbstractObject studentZaIzmjenu;

    public SOAzurirajStudenta(AbstractObject studentZaIzmjenu) {
        this.studentZaIzmjenu = studentZaIzmjenu;
    }
    
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        dbb.azurirajObjekat(studentZaIzmjenu);
    }

    public AbstractObject getStudentZaIzmjenu() {
        return studentZaIzmjenu;
    }
    
    
    
}
