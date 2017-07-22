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
public class SOObrisiStudenta extends AbstractSO {

    AbstractObject studentZaBrisanje;

    public SOObrisiStudenta(AbstractObject studentZaBrisanje) {
        this.studentZaBrisanje = studentZaBrisanje;
    }
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        dbb.obrisiObjekat(studentZaBrisanje);
    }

    public AbstractObject getStudentZaBrisanje() {
        return studentZaBrisanje;
    }
    
}
