/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import Class.PersonAttendance;
import entity.Event;
import java.util.List;
import javax.ejb.Local;
import util.exception.NoResultException;
import util.exception.PersonExistException;

/**
 *
 * @author Sharlynn
 */
@Local
public interface EventAttendanceSessionBeanLocal {

    public void setAttendance(Long eId, Long pId) throws PersonExistException, NoResultException;

//    public List<PersonAttendance> getPersonAttendanceList(Long id);
    public List<Event> getRegisteredEventList(Long pId);

    public void unregisterEvent(Long eId, Long pId);

    public List<PersonAttendance> getAttendanceListOfEvents(Long eId) throws NoResultException;
}
