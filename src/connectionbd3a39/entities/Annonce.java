package connectionbd3a39.entities;


import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GAMERS
 */
public class Annonce {
    private int id;
    private String titre;
    private String descAnnonce;
    private Date dateCreation;
    private Date dateModification;
    private String img;
    private Boolean selected;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Annonce(int id, String titre, String descAnnonce, Date dateCreation, Date dateModification, String img) {
        this.id = id;
        this.titre = titre;
        this.descAnnonce = descAnnonce;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.img = img;
    }

    public Annonce(String titre, String descAnnonce, Date dateCreation, Date dateModification, String img) {
        this.titre = titre;
        this.descAnnonce = descAnnonce;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.img = img;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.id;
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
        final Annonce other = (Annonce) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Annonce{" + "titre=" + titre + ", descAnnonce=" + descAnnonce + ", img=" + img + '}';
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescAnnonce() {
        return descAnnonce;
    }

    public void setDescAnnonce(String descAnnonce) {
        this.descAnnonce = descAnnonce;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Annonce() {
    }
    
}
