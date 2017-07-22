/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Fakultet;
import domen.Student;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author FILIP
 */
public class SOUcitajSveStudente extends AbstractSO {

    List<AbstractObject> studenti;
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        studenti = dbb.vratiSveObjekte(new Student());
        
        Student s = new Student();
        int i = -1;
        
        for (AbstractObject student : studenti) {
            if(student instanceof Student) {
                s = (Student) student;
                i = s.getFakultet().getFakultetID();
            }
            if(i == -1) {
                throw new ServerException("Greska pri ucitavanju fakulteta za studenta! [i=-1]");
            }
            Fakultet fakultet = (Fakultet) dbb.vratiObjekatPoKljucu(new Fakultet(), i);
            s.setFakultet(fakultet);
        }
        if(studenti == null) 
            throw new ServerException("Greska pri ucitavanju studenata iz baze!");
    }

    public List<AbstractObject> getStudenti() {
        return studenti;
    }

    
    
    
}
