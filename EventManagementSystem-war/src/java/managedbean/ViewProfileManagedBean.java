/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import ejb.session.stateless.PersonSessionBeanLocal;
import entity.Event;
import entity.Person;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import util.exception.NoResultException;

/**
 *
 * @author Sharlynn
 */
@Named(value = "viewProfileManagedBean")
@ViewScoped
public class ViewProfileManagedBean implements Serializable {

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

    private Person selectedOtherUser;
    
    private List<Event> listOfEventByUser;
    
    private Event selectedEvent;

    public ViewProfileManagedBean() {
    }

    @PostConstruct
    public void populateUserProfile() {
        try {
            Long userId = (long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
            selectedOtherUser = personSessionBeanLocal.getPerson(userId);
            listOfEventByUser = selectedOtherUser.getListOfEvent();
        } catch (NoResultException ex){
            
        }
    }

    public Person getSelectedOtherUser() {
        return selectedOtherUser;
    }

    public void setSelectedOtherUser(Person selectedOtherUser) {
        this.selectedOtherUser = selectedOtherUser;
    }

    public List<Event> getListOfEventByUser() {
        return listOfEventByUser;
    }

    public void setListOfEventByUser(List<Event> listOfEventByUser) {
        this.listOfEventByUser = listOfEventByUser;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
    
    

}
