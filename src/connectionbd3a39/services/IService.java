/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author GAMERS
 */
public interface IService <E>{
    public void ajouter(E e) throws SQLException;
    public void modifier (E e, int id) throws SQLException;
    public void supprimer (int id) throws SQLException;
    public List<E> getAll () throws SQLException;
    public E getOneById(int id) throws SQLException;
}
