/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import Class.PersonAttendance;
import entity.Person;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import ejb.session.stateless.EventAttendanceSessionBeanLocal;

/**
 *
 * @author Sharlynn
 */
@Named(value = "eventAttendanceManagedBean")
@ViewScoped
public class EventAttendanceManagedBean implements Serializable {

    private List<PersonAttendance> listOfAttendees;

    private List<PersonAttendance> filteredAttendees;

    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    @EJB
    private EventAttendanceSessionBeanLocal eventAttendanceSessionBeanLocal;

    private long eId;

    private long pId;

    private boolean globalFilterOnly = false;

    public EventAttendanceManagedBean() {
    }

    @PostConstruct
    public void populateAttendanceList() {
        pId = authenticationManagedBean.getUserId();
        try {
            eId = (long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
            listOfAttendees = eventAttendanceSessionBeanLocal.getAttendanceListOfEvents(eId);

        } catch (Exception ex) {

        }

    }

    public List<PersonAttendance> getListOfAttendees() {
        return listOfAttendees;
    }

    public void setListOfAttendees(List<PersonAttendance> listOfAttendees) {
        this.listOfAttendees = listOfAttendees;
    }

    public List<PersonAttendance> getFilteredAttendees() {
        return filteredAttendees;
    }

    public void setFilteredAttendees(List<PersonAttendance> filteredAttendees) {
        this.filteredAttendees = filteredAttendees;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }

    public long geteId() {
        return eId;
    }

    public void seteId(long eId) {
        this.eId = eId;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

}
