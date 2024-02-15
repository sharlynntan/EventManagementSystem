/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package session;

import entity.Person;
import exception.NoResultException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sharlynn
 */
@Stateless
public class PersonSessionBean implements PersonSessionBeanLocal {

    @PersistenceContext(unitName = "EventManagementSystem-ejbPU")
    private EntityManager em;

    public Person getPerson(Long id) throws NoResultException {
        Person p = em.find(Person.class, id);

        if (p != null) {
            return p;
        } else {
            throw new NoResultException("Person cannot be found!");
        }

    }

    public void createPerson(Person p) {
        try {
            em.persist(p);
            em.flush();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void updatePerson(Person p) throws NoResultException {
        try {
            Person personToUpdate = getPerson(p.getId());
            if (personToUpdate != null) {
                personToUpdate.setFirstName(p.getFirstName());
                personToUpdate.setContactNumber(p.getContactNumber());
                personToUpdate.setEmail(p.getEmail());
                // to make set person to a seperate method
                personToUpdate.setPassword(p.getPassword());
            }
        } catch (NoResultException ex) {
            throw ex;
        }

    }
    
    public void deletePerson(Long pId) throws NoResultException {
        try {
            Person personToDelete = getPerson(pId);
            em.remove(personToDelete);
        } catch (NoResultException ex) {
            throw ex;
        }
    }

}
