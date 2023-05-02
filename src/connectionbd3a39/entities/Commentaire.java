/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.entities;

import java.sql.Date;

/**
 *
 * @author GAMERS
 */
public class Commentaire {

    public Commentaire(int id, String Contenu, Date dateCreation, Date dateModification, int votes, int annonceId) {
        this.id = id;
        this.Contenu = Contenu;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.votes = votes;
    }

    public Commentaire(int id, String Contenu, Date dateCreation, Date dateModification, int votes) {
        this.id = id;
        this.Contenu = Contenu;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.votes = votes;
    }

    public int getAnnonceId() {
        return annonceId;
    }

    public void setAnnonceId(int annonceId) {
        this.annonceId = annonceId;
    }

    public Commentaire() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Commentaire other = (Commentaire) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String Contenu) {
        this.Contenu = Contenu;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    private int id;
    private String Contenu;
    private Date dateCreation;
    private Date dateModification;
    private int votes;
    private int annonceId;

}
