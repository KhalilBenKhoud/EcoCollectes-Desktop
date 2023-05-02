/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.edu.esprit.entities.Personne;
import tn.edu.esprit.utils.DataSource;

/**
 *
 * @author abdelazizmezri
 */
public class ServicePersonne implements IService<Personne>{
    
    private DataSource ds = DataSource.getInstance();

    @Override
    public void ajouter(Personne p) throws SQLException{
        String req = "INSERT INTO `personne` (`nom`, `prenom`) VALUES ('"+p.getNom()+"', '"+p.getPrenom()+"')";
        Statement st = ds.getCnx().createStatement();
        st.executeUpdate(req);
    }
    
    public void ajouter2(Personne p) throws SQLException{
        String req = "INSERT INTO `personne` (`nom`, `prenom`) VALUES (?,?)";
        PreparedStatement st = ds.getCnx().prepareStatement(req);
        st.setString(1, p.getNom());
        st.setString(2, p.getPrenom());
        
        st.executeUpdate();
    }

    @Override
    public void modifier(Personne p) throws SQLException{
        String req = "UPDATE `personne` SET `nom` = '"+p.getNom()+"', `prenom` = '"+p.getPrenom()+"' WHERE `personne`.`id` = "+p.getId();
        Statement st = ds.getCnx().createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void supprimer(int id) throws SQLException{
        String req = "DELETE FROM `personne` WHERE id ="+id;
        Statement st = ds.getCnx().createStatement();
        st.executeUpdate(req);
    }

    @Override
    public List<Personne> getAll() throws SQLException{
        List<Personne> list = new ArrayList<>();
        
        String req = "Select * from personne";
        Statement st = ds.getCnx().createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next()){
            Personne p = new Personne(rs.getInt("id"), rs.getString(2), rs.getString(3));
            list.add(p);
        }
        
        return list;
    }
    
    
}
