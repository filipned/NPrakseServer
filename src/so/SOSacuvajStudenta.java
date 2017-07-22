/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Student;
import exception.ServerException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FILIP
 */
public class SOSacuvajStudenta extends AbstractSO {

    AbstractObject student;

    public SOSacuvajStudenta(AbstractObject student) {
        this.student = student;
    }

    public AbstractObject getStudent() {
        return student;
    }
    
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        if(student == null)
            throw new ServerException("Greska!");
        try {
            List<AbstractObject> studenti = dbb.vratiSveObjekte(new Student());
            if(studenti.contains(student)) 
                throw new ServerException("Student vec postoji!");
            student = dbb.sacuvajObjekat(student);
            if(student == null)
                throw new ServerException("Neuspjesan unos!");
        } catch (SQLException ex) {
            Logger.getLogger(SOSacuvajStudenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
