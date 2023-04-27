/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import java.util.List;

/**
 *
 * @author hamza
 */
public interface IContratService<T> {

    public void ajouter(T t);

    public void modifier(T t, int id);

    public void supprimer(int id);

    public T afficherContratById(int id);

    public List<T> afficherContrats(int enterprise_id);

    public List<T> rechercher(String x);

}
