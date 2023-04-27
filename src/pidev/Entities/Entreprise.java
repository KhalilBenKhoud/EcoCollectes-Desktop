/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

import java.util.Date;

/**
 *
 * @author hamza
 */
public class Entreprise {
    
    private int id;
    private User ceo_id;
    private String nom;
    private String adresse;
    private int numtel;
    private Date date_creation;
    private String adresse_email;
    private String type_entreprise;

    public Entreprise(int id, User ceo_id, String nom, String adresse, int numtel, Date date_creation, String adresse_email, String type_entreprise) {
        this.id = id;
        this.ceo_id = ceo_id;
        this.nom = nom;
        this.adresse = adresse;
        this.numtel = numtel;
        this.date_creation = date_creation;
        this.adresse_email = adresse_email;
        this.type_entreprise = type_entreprise;
    }

    public Entreprise(User ceo_id, String nom, String adresse, int numtel, Date date_creation, String adresse_email, String type_entreprise) {
        this.ceo_id = ceo_id;
        this.nom = nom;
        this.adresse = adresse;
        this.numtel = numtel;
        this.date_creation = date_creation;
        this.adresse_email = adresse_email;
        this.type_entreprise = type_entreprise;
    }

    public Entreprise() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCeo_id() {
        return ceo_id;
    }

    public void setCeo_id(User ceo_id) {
        this.ceo_id = ceo_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getAdresse_email() {
        return adresse_email;
    }

    public void setAdresse_email(String adresse_email) {
        this.adresse_email = adresse_email;
    }

    public String getType_entreprise() {
        return type_entreprise;
    }

    public void setType_entreprise(String type_entreprise) {
        this.type_entreprise = type_entreprise;
    }

    @Override
    public String toString() {
        return "Entreprise{" + "id=" + id + ", ceo_id=" + ceo_id + ", nom=" + nom + ", adresse=" + adresse + ", numtel=" + numtel + ", date_creation=" + date_creation + ", adresse_email=" + adresse_email + ", type_entreprise=" + type_entreprise + '}';
    }
    
    
    
    
    
}
