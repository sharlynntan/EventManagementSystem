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
import java.util.Map;

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

            System.out.println("hfhshfjshfjd Test");
        } catch (Exception ex) {
            System.out.println("erorrrrrrrrrrrrrrrrrrrrrr");
        }

    }
    
    public void populateAttendanceList(Long id) {
        try {
            listOfAttendees = eventAttendanceSessionBeanLocal.getAttendanceListOfEvents(id);

            System.out.println("hfhshfjshfjd Test");
        } catch (Exception ex) {
            System.out.println("erorrrrrrrrrrrrrrrrrrrrrr");
        }
    }
    
    public String goToViewProfilePage() {
         FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext()
                .getRequestParameterMap();
        String rIdStr = params.get("prId");
        Long peId = Long.parseLong(rIdStr);
        FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .put("param", peId);
        return "externalProfilePage.xhtml?faces-redirect=true"; 
    }

    public void markPresent() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext()
                .getRequestParameterMap();
        String pIdStr = params.get("personId");
        Long personId = Long.parseLong(pIdStr);
//        System.out.println("event id: " + eId);
//        System.out.println("person id: " + personId);
        eventAttendanceSessionBeanLocal.updateAttendance(eId, personId, true);
        populateAttendanceList(eId);
        
    }

    public void markAbsent() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext()
                .getRequestParameterMap();
        String pIdStr = params.get("pIdentify");
        Long personId = Long.parseLong(pIdStr);
        eventAttendanceSessionBeanLocal.updateAttendance(eId, personId, false);
        populateAttendanceList(eId);
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
