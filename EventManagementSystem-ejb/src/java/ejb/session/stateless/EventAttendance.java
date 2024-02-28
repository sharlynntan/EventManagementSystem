/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import Class.PersonAttendance;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sharlynn
 */
@Stateless
public class EventAttendance implements EventAttendanceLocal {

    @PersistenceContext(unitName = "EventManagementSystem-ejbPU")
    private EntityManager em;
    
    
    public List<PersonAttendance> getPersonAttendanceList(Long id) {
        Query query = em.createQuery("SELECT e.attendanceList FROM Event e WHERE e.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    public void setAttendance() {
        
    }

    

    
}
