/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import domen.AbstractObject;
import domen.Zaposleni;
import exception.ServerException;
import java.util.List;
import so.SOAzurirajKompaniju;
import so.SOAzurirajStudenta;
import so.SOObrisiKompaniju;
import so.SOObrisiStudenta;
import so.SOSacuvajStudenta;
import so.SOSacuvajKompaniju;
import so.SOSacuvajPraksu;
import so.SOUcitajSveBranse;
import so.SOUcitajSveFakultete;
import so.SOUcitajSveKompanije;
import so.SOUcitajSvePrakse;
import so.SOUcitajSveStudente;
import so.SOUlogujZaposlenog;

/**
 *
 * @author FILIP
 */
public class Controler {
    
    private static Controler instance = null;
    
    private Controler() {
        
    }
    
    public static Controler getInstance() {
        if(instance == null) {
            instance = new Controler();
        }
        return instance;
    }

    public AbstractObject ulogujZaposlenog(Zaposleni zaposleni) throws ServerException {
        SOUlogujZaposlenog so = new SOUlogujZaposlenog(zaposleni);
        so.izvrsiOperaciju();
        return so.getZaposleni();
    }

    public List<AbstractObject> ucitajSveFakultete() throws ServerException {
        SOUcitajSveFakultete so = new SOUcitajSveFakultete();
        so.izvrsiOperaciju();
        return so.getFakulteti();
    }

    public AbstractObject sacuvajStudenta(AbstractObject student) throws ServerException {
        SOSacuvajStudenta so = new SOSacuvajStudenta(student);
        so.izvrsiOperaciju();
        return so.getStudent();
    }

    public List<AbstractObject> ucitajSveBranse() throws ServerException {
        SOUcitajSveBranse so = new SOUcitajSveBranse();
        so.izvrsiOperaciju();
        return so.getBranse();
    }

    public AbstractObject sacuvajKompaniju(AbstractObject kompanija) throws ServerException {
        SOSacuvajKompaniju so = new SOSacuvajKompaniju(kompanija);
        so.izvrsiOperaciju();
        return so.getKompanija();
    }
    
    public List<AbstractObject> ucitajSveStudente() throws ServerException {
        SOUcitajSveStudente so = new SOUcitajSveStudente();
        so.izvrsiOperaciju();
        return so.getStudenti();
    }
    public List<AbstractObject> ucitajSveKompanije() throws ServerException {
        SOUcitajSveKompanije so = new SOUcitajSveKompanije();
        so.izvrsiOperaciju();
        return so.getKompanije();
    }

    public AbstractObject obrisiStudenta(AbstractObject student) throws ServerException {
        SOObrisiStudenta so = new SOObrisiStudenta(student);
        so.izvrsiOperaciju();
        return so.getStudentZaBrisanje();
    }

    public AbstractObject azurirajStudenta(AbstractObject student) throws ServerException {
        SOAzurirajStudenta so = new SOAzurirajStudenta(student);
        so.izvrsiOperaciju();
        return so.getStudentZaIzmjenu();
    }

    public AbstractObject obrisiKompaniju(AbstractObject kompanija) throws ServerException {
        SOObrisiKompaniju so = new SOObrisiKompaniju(kompanija);
        so.izvrsiOperaciju();
        return so.getKompanijaZaBrisanje();
    }

    public AbstractObject azurirajKompaniju(AbstractObject kompanija) throws ServerException {
        SOAzurirajKompaniju so = new SOAzurirajKompaniju(kompanija);
        so.izvrsiOperaciju();
        return so.getKompanijaZaIzmjenu();
    }

    public AbstractObject sacuvajPraksu(AbstractObject praksa) throws ServerException {
        SOSacuvajPraksu so = new SOSacuvajPraksu(praksa);
        so.izvrsiOperaciju();
        return so.getPraksa();
    }
    
    public List<AbstractObject> ucitajSvePrakse() throws ServerException {
        SOUcitajSvePrakse so = new SOUcitajSvePrakse();
        so.izvrsiOperaciju();
        return (List<AbstractObject>) so.getPrakse();
    }
}
