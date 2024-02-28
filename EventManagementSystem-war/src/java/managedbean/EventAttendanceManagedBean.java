/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import entity.Person;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Sharlynn
 */
@Named(value = "eventAttendanceManagedBean")
@ViewScoped
public class EventAttendanceManagedBean implements Serializable {

    private List<Person> listOfAttendees;
    
    private List<Person> filteredAttendees;
    
    private boolean globalFilterOnly  = false;
    
    public EventAttendanceManagedBean() {
    }

    public List<Person> getListOfAttendees() {
        return listOfAttendees;
    }

    public void setListOfAttendees(List<Person> listOfAttendees) {
        this.listOfAttendees = listOfAttendees;
    }

    public List<Person> getFilteredAttendees() {
        return filteredAttendees;
    }

    public void setFilteredAttendees(List<Person> filteredAttendees) {
        this.filteredAttendees = filteredAttendees;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }
    
    
    
}
