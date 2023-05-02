/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.tests;

import connectionbd3a39.entities.Annonce;
import connectionbd3a39.services.ServiceAnnonce;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author GAMERS
 */
public class TestAnnonce extends ServiceAnnonce {

    public void testCruds(){        
        Annonce a = new Annonce("titre", "description", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "img");

        ServiceAnnonce serv = new ServiceAnnonce();

        try {
            serv.ajouter(a);

            System.out.println("-------------------------------");
            try {
                List<Annonce> annonces = serv.getAll();
                
                Annonce result1 = annonces.get(annonces.size() - 1);
                Annonce result2 = serv.getOneById(result1.getId());
                
                if (result1.equals(result2)) {
                    System.out.println("-------------------------------");
                }
                try {
                    Annonce b = new Annonce("titre2", "description2", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "img");
                    int id = result1.getId();
                    
                    serv.modifier(b, id);
                                        
                    System.out.println("-------------------------------");
                    try {
                        serv.supprimer(id);
                        
                        System.out.println("-------------------------------");

                    } catch (SQLException e) {
                        
                        System.out.println("Erreur lors de la suppression!");
                    }
                } catch (SQLException e) {
                    
                    System.out.println("Erreur lors de la modification!");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Erreur lors du retrait!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Erreur lors de l'ajout!");
        }
    }
}
