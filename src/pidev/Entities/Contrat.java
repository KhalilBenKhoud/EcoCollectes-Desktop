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
public class Contrat {
    
    private int id;
    private Entreprise enterprise_id;
    private User collecteur_id;
    private String description;
    private Date date_debut;
    private Date date_fin;
    private String statut_contrat;
    private String type_contrat;
    private float montant;

    public Contrat(int id, Entreprise enterprise_id, User collecteur_id, String description, Date date_debut, Date date_fin, String statut_contrat, String type_contrat, float montant) {
        this.id = id;
        this.enterprise_id = enterprise_id;
        this.collecteur_id = collecteur_id;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut_contrat = statut_contrat;
        this.type_contrat = type_contrat;
        this.montant = montant;
    }

    public Contrat(Entreprise enterprise_id, User collecteur_id, String description, Date date_debut, Date date_fin, String statut_contrat, String type_contrat, float montant) {
        this.enterprise_id = enterprise_id;
        this.collecteur_id = collecteur_id;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut_contrat = statut_contrat;
        this.type_contrat = type_contrat;
        this.montant = montant;
    }

    public Contrat() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entreprise getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(Entreprise enterprise_id) {
        this.enterprise_id = enterprise_id;
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

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getStatut_contrat() {
        return statut_contrat;
    }

    public void setStatut_contrat(String statut_contrat) {
        this.statut_contrat = statut_contrat;
    }

    public String getType_contrat() {
        return type_contrat;
    }

    public void setType_contrat(String type_contrat) {
        this.type_contrat = type_contrat;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }


 
   
    
    
}
