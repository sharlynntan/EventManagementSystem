/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import Class.Address;
import Class.PersonAttendance;
import ejb.session.stateless.EventAttendanceSessionBeanLocal;
import entity.Event;
import util.enumeration.eventCategory;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import ejb.session.stateless.EventSessionBeanLocal;
import entity.Person;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import util.exception.NoResultException;

/**
 *
 * @author Sharlynn
 */
@Named(value = "eventManagedBean")
@ViewScoped
public class EventManagedBean implements Serializable {

    @EJB
    private EventSessionBeanLocal eventSessionBeanLocal;

    @Inject
    private PersonManagedBean personManagedBean;

    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    @Inject
    private CreatedEventManagedBean createdManagedBean;

    @EJB
    private EventAttendanceSessionBeanLocal eventAttendanceLocal;

//    private boolean globalFilterOnly = false;
    private String searchTerm = "";

//    private Event selectedEvent;
//
    private List<Event> filteredEvent;

    private String title;

    private Date convertedDate;

    private String eventDate;

    private String evenTime;

    private String street1;

    private String street2;

    private String city;

    private String postalCode;

    private String description;

    private Date convertedDeadline;

    private String deadline;

    private String deadlineTime;

    private String eventCat;

    private int maxPax;

    private int estimateDurationMins;

    public EventManagedBean() {
    }

    @PostConstruct
    public void searchEventList() {
        filteredEvent = (List<Event>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("event");
    }

    public List<Event> getAllEvent() {
        return eventSessionBeanLocal.getAllEvents();
    }

    public void addEvent() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Event e = new Event();
            e.setTitle(title);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(eventDate + " " + evenTime, formatter);
            convertedDate = Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());

            LocalDateTime deadline2 = LocalDateTime.parse(deadline + " " + deadlineTime, formatter);
            convertedDeadline = Date.from(deadline2.atZone(java.time.ZoneId.systemDefault()).toInstant());
            System.out.println(convertedDate);
            System.out.println(convertedDeadline);

            e.setDate(convertedDate);
            e.setDeadline(convertedDeadline);
            e.setDescription(description);
            eventCategory enumtype = getEnumCategory();
            e.setEventCategory(enumtype);
            e.setMaxPax(maxPax);
            e.setEstimateDurationMins(estimateDurationMins);
            // Create Address
            Address a = new Address(street1, street2, city, postalCode);
            e.setLocation(a);
            List<PersonAttendance> emptyList = new ArrayList<PersonAttendance>();
            e.setAttendanceList(emptyList);
            long pId = authenticationManagedBean.getUserId();
            Person p = personManagedBean.getPersonWithId(pId);
            e.setOrganiser(p);
            eventSessionBeanLocal.createEvent(e);
            context.addMessage(null, new FacesMessage("Success",
                    "Successfully created event!"));
            createdManagedBean.populateCreatedEvents();

        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to add event"));
        }

//        getUserCreatedEvent();
    }

//    public String handleSearch() {
//        filteredEvent = getAllEvent();
//        if (!searchTerm.isEmpty()) {
//            filteredEvent = eventSessionBeanLocal.getFilteredEvent(searchTerm);
//        }
//
//        FacesContext context = FacesContext.getCurrentInstance();
//        FacesContext.getCurrentInstance().getExternalContext().getFlash()
//                .put("event", filteredEvent);
//
//        searchTerm = "";
//        return "searchResult.xhtml?faces-redirect=true";
//    }
//    public void getUserCreatedEvent() {
//        long pId = authenticationManagedBean.getUserId();
////        System.out.println("hsjhdad" + pId);
//        createdEventList = eventSessionBeanLocal.getUserCreatedEvent(pId);
////        return createdEventList;
//    }
    public eventCategory getEnumCategory() {
        eventCategory ec;

        switch (eventCat) {
            case "Music":
                ec = eventCategory.MUSIC;
                break;
            case "Nightlife":
                ec = eventCategory.NIGHTLIFE;
                break;
            case "Performing Art":
                ec = eventCategory.PERFORMINGART;
                break;
            case "Holidays":
                ec = eventCategory.HOLIDAYS;
                break;
            case "Health":
                ec = eventCategory.HEALTH;
                break;
            case "Hobbies":
                ec = eventCategory.HOBBIES;
                break;
            case "Business":
                ec = eventCategory.BUSINESS;
                break;
            case "Food and Drink":
                ec = eventCategory.FOODANDDRINK;
                break;
            default:
                ec = eventCategory.OTHER;
        }
        return ec;
    }

    public String getOrganiserName(long id) {
        System.out.println("hellllo" + id);
        try {
            Event e = eventSessionBeanLocal.getEvent(id);
            return e.getOrganiser().getFirstName();
        } catch (NoResultException ex) {
            return "Organiser Not Found";
        }

    }

    public List<Long> checkEventStatus() {
        // return true if registered
        List<Event> eventList = eventAttendanceLocal.getRegisteredEventList(authenticationManagedBean.getUserId());
        System.out.println("sssssssssssssssssssssssssssss" + eventList.size());
        List<Long> idList = new ArrayList<>();
        for (Event e : eventList) {
            idList.add(e.getId());
        }

        return idList;
    }

    public List<Event> getFilteredEvent() {
        return filteredEvent;
    }

    public void setFilteredEvent(List<Event> filteredEvent) {
        this.filteredEvent = filteredEvent;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public Date getEventDate() {;
//        return eventDate;
//    }
//
//    public void setEventDate(Date eventDate) {
//        this.eventDate = eventDate;
//    }
    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Date getDeadline() {
//        return deadline;
//    }
//
//    public void setDeadline(Date deadline) {
//        this.deadline = deadline;
//    }
    public String getEventCat() {
        return eventCat;
    }

    public void setEventCat(String eventCat) {
        this.eventCat = eventCat;
    }

//    public Event getSelectedEvent() {
//        return selectedEvent;
//    }
//
//    public void setSelectedEvent(Event selectedEvent) {
//        this.selectedEvent = selectedEvent;
//    }
//
//    public List<Event> getCreatedEventList() {
//        return createdEventList;
//    }
//
//    public void setCreatedEventList(List<Event> createdEventList) {
//        this.createdEventList = createdEventList;
//    }
    public int getMaxPax() {
        return maxPax;
    }

    public void setMaxPax(int maxPax) {
        this.maxPax = maxPax;
    }

    public int getEstimateDurationMins() {
        return estimateDurationMins;
    }

    public void setEstimateDurationMins(int estimateDurationMins) {
        this.estimateDurationMins = estimateDurationMins;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Date getConvertedDate() {
        return convertedDate;
    }

    public void setConvertedDate(Date convertedDate) {
        this.convertedDate = convertedDate;
    }

    public String getEvenTime() {
        return evenTime;
    }

    public void setEvenTime(String evenTime) {
        this.evenTime = evenTime;
    }

    public Date getConvertedDeadline() {
        return convertedDeadline;
    }

    public void setConvertedDeadline(Date convertedDeadline) {
        this.convertedDeadline = convertedDeadline;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

}
