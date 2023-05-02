package connectionbd3a39.services;

import connectionbd3a39.entities.Annonce;
import connectionbd3a39.entities.Commentaire;
import connectionbd3a39.tools.DataBase;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author GAMERS
 */
public class ServiceAnnonce implements IService<Annonce> {

    Connection cnx = DataBase.getInstance().getCnx();

    @Override
    public void ajouter(Annonce a) throws SQLException {
        String req = "INSERT INTO annonce (titre,description,date_creation, date_modification, img_url) VALUES (?,?,?,?,?)";

        PreparedStatement st = cnx.prepareStatement(req);

        st.setString(1, a.getTitre());
        st.setString(2, a.getDescAnnonce());
        st.setDate(3, a.getDateCreation());
        st.setDate(4, a.getDateModification());
        st.setString(5, a.getImg());

        st.executeUpdate();
        System.out.println("L'Ajout fonctionne correctement!");

    }

    @Override
    public void modifier(Annonce a, int id) throws SQLException {

        try {
            String req = "UPDATE annonce SET titre=?, description=?, date_creation=? , date_modification=?, img_url=? WHERE id=" + id;

            PreparedStatement st = cnx.prepareStatement(req);

            st.setString(1, a.getTitre());
            st.setString(2, a.getDescAnnonce());
            st.setDate(3, a.getDateCreation());
            st.setDate(4, a.getDateModification());
            st.setString(5, a.getImg());

            st.executeUpdate();
            System.out.println("id:" + id + " a été modifié");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {

        try {
            String req = "delete from annonce where Id=" + id;
            PreparedStatement st = cnx.prepareStatement(req);
            st.executeUpdate();
            System.out.println("id=" + id + " supprimée");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Annonce> getAll() throws SQLException {
        List<Annonce> list = new ArrayList<>();

        String req = "select * from annonce";

        Statement st = cnx.createStatement();

        ResultSet set = st.executeQuery(req);
        while (set.next()) {
            int id = set.getInt("id");
            String titre = set.getString(2);
            String description = set.getString(3);
            Date dateCreation = set.getDate(4);
            Date dateModification = set.getDate(5);
            String img = set.getString(6);

            Annonce annonce = new Annonce(id, titre, description, dateCreation, dateModification, img);
            list.add(annonce);
        }

        System.out.println("Le retrait fonctionne correctement!");

        return list;
    }

    public List<Commentaire> getAllCommentaires(int annonce) throws SQLException {
        List<Commentaire> list = new ArrayList<>();
        String req = "SELECT * FROM Commentaire WHERE annonce_id = " + annonce;
        System.out.println("fetching commentaires for id:" + annonce);

        Statement st = cnx.createStatement();

        ResultSet set = st.executeQuery(req);
        while (set.next()) {
            int id = set.getInt("id");
            String contenu = set.getString(2);
            Date dateCreation = set.getDate(3);
            Date dateModification = set.getDate(4);
            int votes = set.getInt(5);

            Commentaire commentaire = new Commentaire(id, contenu, dateCreation, dateModification, votes, annonce);
            list.add(commentaire);
        }

        System.out.println("Le retrait fonctionne correctement!");

        return list;
    }

    @Override
    public Annonce getOneById(int id) throws SQLException {
        String req = "SELECT * FROM annonce WHERE `Id`=" + id;
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Annonce a = new Annonce();
            a.setId(rs.getInt(1));
            a.setTitre(rs.getString("titre"));
            a.setDescAnnonce(rs.getString("description"));
            a.setDateCreation(rs.getDate("date_creation"));
            a.setDateModification(rs.getDate("date_modification"));
            a.setImg(rs.getString("img_url"));
            return a;
        }

        System.out.println("Le retrait fonctionne correctement!");
        return null;
    }
}
