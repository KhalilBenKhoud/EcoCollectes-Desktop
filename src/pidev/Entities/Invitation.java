/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

import java.sql.Date;

/**
 *
 * @author hamza
 */
public class Invitation {
    
    private int id;
    private Contrat contrat_id;
    private User collecteur_id;
    private String description;
    private Date date_invitation;
    private String statut_invitation;

    public Invitation(int id, Contrat contrat_id, User collecteur_id, String description, Date date_invitation, String statut_invitation) {
        this.id = id;
        this.contrat_id = contrat_id;
        this.collecteur_id = collecteur_id;
        this.description = description;
        this.date_invitation = date_invitation;
        this.statut_invitation = statut_invitation;
    }

    public Invitation(Contrat contrat_id, User collecteur_id, String description, Date date_invitation, String statut_invitation) {
        this.contrat_id = contrat_id;
        this.collecteur_id = collecteur_id;
        this.description = description;
        this.date_invitation = date_invitation;
        this.statut_invitation = statut_invitation;
    }

    public Invitation() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contrat getContrat_id() {
        return contrat_id;
    }

    public void setContrat_id(Contrat contrat_id) {
        this.contrat_id = contrat_id;
    }

    public User getCollecteur_id() {
        return collecteur_id;
    }

    public void setCollecteur_id(User collecteur_id) {
        this.collecteur_id = collecteur_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_invitation() {
        return date_invitation;
    }

    public void setDate_invitation(Date date_invitation) {
        this.date_invitation = date_invitation;
    }

    public String getStatut_invitation() {
        return statut_invitation;
    }

    public void setStatut_invitation(String statut_invitation) {
        this.statut_invitation = statut_invitation;
    }

    @Override
    public String toString() {
        return "Invitation{" + "id=" + id + ", contrat_id=" + contrat_id + ", collecteur_id=" + collecteur_id + ", description=" + description + ", date_invitation=" + date_invitation + ", statut_invitation=" + statut_invitation + '}';
    }
    
    
    
    
    
}
