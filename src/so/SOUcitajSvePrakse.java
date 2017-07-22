/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.AbstractObject;
import domen.Bransa;
import domen.Kompanija;
import domen.Praksa;
import domen.Student;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author FILIP
 */
    public class SOUcitajSvePrakse extends AbstractSO {
    
    List<AbstractObject> prakse;
    
    @Override
    protected void izvrsiKonkretnuOperaciju() throws ServerException {
        prakse = dbb.vratiSveObjekte(new Praksa());
        
        for (AbstractObject p : prakse) {
            Praksa praksa = (Praksa) p;
            Student s = (Student) dbb.vratiObjekatPoKljucu(new Student(), praksa.getStudent().getStudentID());
            Kompanija k = (Kompanija) dbb.vratiObjekatPoKljucu(new Kompanija(), praksa.getKompanija().getKompanijaID());
            Bransa b = (Bransa) dbb.vratiObjekatPoKljucu(new Bransa(), praksa.getBransa().getBransaID());
            praksa.setStudent(s);
            praksa.setKompanija(k);
            praksa.setBransa(b);
        }
        
        if(prakse == null) 
            throw new ServerException("Greska pri ucitavanju praksi iz baze!");
    }

    public List<AbstractObject> getPrakse() {
        return prakse;
    }
}
