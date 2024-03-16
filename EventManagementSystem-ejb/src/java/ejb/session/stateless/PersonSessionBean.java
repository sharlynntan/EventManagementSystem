/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Person;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import util.exception.NoResultException;
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

    public Person getPerson(String email) throws NoResultException {
        Query query = em.createQuery("SELECT p FROM Person p WHERE p.email = :email");
        query.setParameter("email", email);
        try {
            return (Person) query.getSingleResult();
        } catch (Exception ex) {
            throw new NoResultException("Email cannot be found!!");
        }

    }

    public void createPerson(Person p) {
        try {
            String pass = p.getPassword();
            p.setPassword(hashPassword(pass));
            em.persist(p);
            em.flush();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();
            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public void updatePerson(Person p) throws NoResultException {
        try {
            Person personToUpdate = getPerson(p.getId());
            if (personToUpdate != null) {
                personToUpdate.setFirstName(p.getFirstName());
                personToUpdate.setLastName(p.getLastName());
                personToUpdate.setContactNumber(p.getContactNumber());
                personToUpdate.setEmail(p.getEmail());
                // to make set person to a seperate method
                personToUpdate.setPassword(p.getPassword());
                personToUpdate.setProfilePictureName(p.getProfilePictureName());
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
