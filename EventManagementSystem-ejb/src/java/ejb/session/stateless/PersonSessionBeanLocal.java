/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Person;
import util.exception.NoResultException;
import javax.ejb.Local;

/**
 *
 * @author Sharlynn
 */
@Local
public interface PersonSessionBeanLocal {

    public Person getPerson(Long id) throws NoResultException;

    public void createPerson(Person p);

    public void updatePerson(Person p) throws NoResultException;

    public void deletePerson(Long pId) throws NoResultException;

    public Person getPerson(String email) throws NoResultException;
    
}
