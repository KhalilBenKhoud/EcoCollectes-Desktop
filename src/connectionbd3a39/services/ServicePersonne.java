/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.services;

import connectionbd3a39.entities.Personne;
import connectionbd3a39.tools.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GAMERS
 */
public class ServicePersonne implements IService<Personne>{

    Connection cnx = DataBase.getInstance().getCnx();
    
    @Override
    public void ajouter(Personne e) throws SQLException{
        String req = "INSERT INTO 'personne'('nom','prenom') VALUES ()";
        
        PreparedStatement st = cnx.prepareStatement(req);
        
        st.setString(1, e.getNom());
        st.setString(2, e.getPrenom());
        
        st.executeUpdate();
    }

    @Override
    public void modifier(Personne e, int id) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimer(int id) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Personne> getAll() throws SQLException{
        List<Personne> list = new ArrayList<>();
        
        String req = "select * from personne";
        
        Statement st = cnx.createStatement();
        
        ResultSet set = st.executeQuery(req);
        while(set.next()){
            int id = set.getInt("id");
            String nom = set.getString(2);
            String prenom = set.getString(3);
            Personne p = new Personne(id, nom, prenom);
            list.add(p);
        }
        
        
        return list;
    }

    @Override
    public Personne getOneById(int id) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
