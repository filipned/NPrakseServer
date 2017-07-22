/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.AbstractObject;
import domen.Bransa;
import domen.Zaposleni;
import exception.ServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FILIP
 */
public class DatabaseBroker {
    
    private Connection connection;

    public DatabaseBroker() {
    }
    
    public void uspostaviKonekciju() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver baze ucitan!");
            String url = "jdbc:mysql://localhost:3306/prakse";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            System.out.println("Uspesno uspostavljanje konekcije!");
        } catch (ClassNotFoundException ex) {
//            throw new ServerskiException("Drajver nije pronadjen!");
        } catch (SQLException ex) {
//            throw new ServerskiException("Konekcija na bazu nije uspela!");
        }
    }
    
    public void raskiniKonekciju() {
        try {
            connection.close();
            System.out.println("Konekcija sa bazom raskinuta!");

        } catch (SQLException ex) {
            System.out.println("Neuspesno raskidanje konekcije!");
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void potvrdiTransakciju(){
        try {
            connection.commit();
            System.out.println("Transakcija potvrdjena");
            
        } catch (SQLException ex) {
            System.out.println("Greska prilikom pottvrdjivanja transakcije!");
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ponistiTransakciju() {
        try {
            connection.rollback();
            System.out.println("Transakcija ponistena");
        
        } catch (SQLException ex) {
            System.out.println("Greska prilikom ponistavanja transakcije!");
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List<AbstractObject> vratiSveObjekte(AbstractObject o) {
        List<AbstractObject> sviZaposleni = new ArrayList<>();
        try {            
            String upit = "SELECT * FROM " + o.vratiImeTabele();
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(upit);
            sviZaposleni = o.RSuTabelu(rs);
            s.close();
            System.out.println("Uspesno izvrsen SELECT!");
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sviZaposleni;
    }

    public AbstractObject sacuvajObjekat(AbstractObject o) throws SQLException {
        
        String upit = String.format("INSERT INTO %s VALUES (%s)", o.vratiImeTabele(), o.vratiParametre());
        
        System.out.println(upit);
        Statement s = connection.createStatement();
        s.executeUpdate(upit);
        ResultSet rs = s.executeQuery("SELECT LAST_INSERT_ID() as last_id from " + o.vratiImeTabele());
        while (rs.next()) {
            String lastid = rs.getString("last_id");
            System.out.println(lastid);
            o.postaviVrednostPK(Integer.parseInt(lastid));
            break;
        }

        s.close();
        return o;
    }

    public void sacuvajUAgregirajucu(AbstractObject kompanija, Bransa bransa, String agregirajuca) throws SQLException {
        
        String upit = String.format("INSERT INTO " + agregirajuca + " VALUES(%s, %s)", bransa.vratiVrednostPK(), kompanija.vratiVrednostPK());
        System.out.println("kompanija id " + kompanija.vratiVrednostPK() + " bransa id " + bransa.vratiVrednostPK());
        Statement s = connection.createStatement();
        s.executeUpdate(upit);
        s.close();
    }
    
    public AbstractObject vratiObjekatPoKljucu(AbstractObject o, int ID) throws ServerException {
        String upit;
        if (o.vratiPK() != null) {
            upit = "SELECT * FROM " + o.vratiImeTabele() + " WHERE " + o.vratiPK() + "=" + ID;
        } else {
            upit = "SELECT * FROM " + o.vratiImeTabele() + " WHERE " + o.vratiSlozenPK();
        }
        try (Statement s = connection.createStatement();) {
            ResultSet rs = s.executeQuery(upit);
            List<AbstractObject> listaObjekata = o.RSuTabelu(rs);
            s.close();
            System.out.println("Uspesno izvrsen mini SELECT");
            return listaObjekata.get(0);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }
    public List<Integer> vratiObjektePoKljucu(AbstractObject o, String imeTabele, String kolona, int ID) throws ServerException {
        String upit;
        if (o.vratiPK() != null) {
            upit = "SELECT "+ kolona +" FROM " + imeTabele +  " WHERE " + o.vratiPK() + "=" + ID;
        } else {
            upit = "SELECT * FROM " + imeTabele + " WHERE " + o.vratiSlozenPK();
        }
        try (Statement s = connection.createStatement();) {
            ResultSet rs = s.executeQuery(upit);
            List<Integer> listaKljuceva = new ArrayList<>();
            
            while(rs.next()) {
                int i = rs.getInt("bransaID");
                listaKljuceva.add(i);
            }
             
            s.close();
            System.out.println("Uspesno izvrsen mini SELECT");
            return listaKljuceva;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }
    
    public AbstractObject obrisiObjekat(AbstractObject o) throws ServerException {
        try {
            String upit = String.format("DELETE FROM %s WHERE %s = %s", o.vratiImeTabele(), o.vratiPK(), o.vratiVrednostPK());
            Statement s = connection.createStatement();
            System.out.println(upit);
            s.executeUpdate(upit);
            potvrdiTransakciju();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
        return o;
    }
    
    public AbstractObject azurirajObjekat(AbstractObject o) throws ServerException {
        List<AbstractObject> lista = vratiSveObjekte(o);

        String upit;

        try {
            upit = String.format("UPDATE %s SET %s WHERE %s = %s", o.vratiImeTabele(), o.vratiUpdate(), o.vratiPK(), o.vratiVrednostPK());
            System.out.println(upit);
            Statement s = connection.createStatement();
            s.executeUpdate(upit);
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }

         
        return o;
    }
}
