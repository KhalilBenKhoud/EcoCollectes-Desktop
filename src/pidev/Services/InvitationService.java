/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pidev.Entities.Contrat;
import pidev.Entities.Invitation;
import pidev.Entities.ContratStatut;
import pidev.Entities.User;
import pidev.Utils.Database;

/**
 *
 * @author hamza
 */
public class InvitationService implements IInvitationService<Invitation> {

    private Connection cnx;
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;

    public InvitationService() {
        cnx = Database.getInstance().getConnection();
    }

    @Override
    public int ajouter(Invitation t) {
        String req = "Insert into invitation (collecteur_id,contrat_id,description,date_invitation,statut_invitation)  values (?,?,?,?,?)";
        try {
           // pst = cnx.prepareStatement(req);
            pst = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, t.getCollecteur_id().getId());
            pst.setInt(2, t.getContrat_id().getId());
            pst.setString(3, t.getDescription());
            pst.setDate(4, new Date(new java.util.Date().getTime()));
            pst.setString(5, "En attente");

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    @Override
    public void modifier(Invitation t, int id) {
        String req = "update invitation set collecteur_id =?,contrat_id =?,description =? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, t.getCollecteur_id().getId());
            pst.setInt(2, t.getContrat_id().getId());
            pst.setString(3, t.getDescription());
            pst.setInt(4, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateStatus(String statut_invitation, int id) {
        String req = "update invitation set statut_invitation=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, statut_invitation);
            pst.setInt(2, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "delete from invitation where id = " + id + " ";
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Invitation afficherInvitationById(int id) {
        String req = "select i.*,c.*,u.* from invitation as i inner join contrat as c on i.contrat_id=c.id inner join user as u on i.collecteur_id=u.id  where i.id=" + id;
        Invitation e = new Invitation();
        try {
            System.out.println("hiiii cnx   " + cnx);
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Contrat c = new Contrat();
                User u = new User();
                /// User
                u.setId(rs.getInt("u.id"));
                u.setEmail(rs.getString("u.email"));
                u.setUsername(rs.getString("u.username"));
                /// Contrat
                c.setId(rs.getInt("c.id"));
                c.setDescription(rs.getString("c.description"));
                c.setDate_debut(rs.getDate("c.date_debut"));
                c.setDate_fin(rs.getDate("c.date_fin"));
                c.setStatut_contrat(rs.getString("c.statut_contrat"));
                c.setType_contrat(rs.getString("c.type_contrat"));
                c.setMontant(rs.getFloat("c.montant"));
                /// Invitation
                e.setId(rs.getInt("i.id"));
                e.setDescription(rs.getString("i.description"));
                e.setDate_invitation(rs.getDate("i.date_invitation"));
                e.setStatut_invitation(rs.getString("i.statut_invitation"));
                e.setCollecteur_id(u);
                e.setContrat_id(c);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }

    @Override
    public List<Invitation> afficherInvitations(int enterprise_id) {
        String req = "select i.*,c.*,u.* from invitation as i inner join contrat as c on i.contrat_id=c.id inner join user as u on i.collecteur_id=u.id  where c.enterprise_id=" + enterprise_id;
        List<Invitation> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Invitation e = new Invitation();
                Contrat c = new Contrat();
                User u = new User();
                /// User
                u.setId(rs.getInt("u.id"));
                u.setEmail(rs.getString("u.email"));
                u.setUsername(rs.getString("u.username"));
                /// Contrat
                c.setId(rs.getInt("c.id"));
                c.setDescription(rs.getString("c.description"));
                c.setDate_debut(rs.getDate("c.date_debut"));
                c.setDate_fin(rs.getDate("c.date_fin"));
                c.setStatut_contrat(rs.getString("c.statut_contrat"));
                c.setType_contrat(rs.getString("c.type_contrat"));
                c.setMontant(rs.getFloat("c.montant"));
                /// Invitation
                e.setId(rs.getInt("i.id"));
                e.setDescription(rs.getString("i.description"));
                e.setDate_invitation(rs.getDate("i.date_invitation"));
                e.setStatut_invitation(rs.getString("i.statut_invitation"));
                e.setCollecteur_id(u);
                e.setContrat_id(c);
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public List<Invitation> rechercher(String x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
