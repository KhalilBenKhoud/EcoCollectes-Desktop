/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eco;

/**
 *
 * @author khalil
 */

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.UserService ;
import java.util.* ;
import entities.User ;

public class Eco {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           System.out.println("Hello World!");
        UserService service = new UserService() ;
        try {
         List<User> list = service.afficher() ;
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        // String roles[] = {"ROLE_DONNEUR"} ;
       //  User p = new User(98541235,"mahmoud","mahmoud@gmail.com","esprit ijk","youssef","homme","") ;
         // service.ajouter(p) ;
         
         
       //   service.delete(22) ;
           // System.out.println(service.findUserById(7).getUsername());
           
           // service.findUserByEmail("asma@gmail.com").setId(13) ;
             System.out.println(service.findUserByEmail("khalil.b.khoud1998@gmail.com").getId());
         
        } catch (SQLException ex) {
          ex.printStackTrace() ;
        }
    }
    
}
