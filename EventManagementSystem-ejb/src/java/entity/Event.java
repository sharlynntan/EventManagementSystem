/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import Class.Address;
import Class.PersonAttendance;
import util.enumeration.eventCategory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sharlynn
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // String dateFormat = "dd/MM/yyyy HH:mm";
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    @Future
    private Date date;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int estimateDurationMins;

    @Embedded
    @AttributeOverrides(value = {
        @AttributeOverride(name = "street1", column = @Column(name = "house_number")),
        @AttributeOverride(name = "street2", column = @Column(name = "street")),
        @AttributeOverride(name = "city", column = @Column(name = "city")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "postalCode"))

    })
    private Address location;

    private String description;

    // String dateFormat = "dd/MM/yyyy HH:mm";
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    @Future
    private Date deadline;

    @NotNull
    private int maxPax;

    private List<PersonAttendance> attendanceList;

    @Enumerated(EnumType.STRING)
    private eventCategory eventCategory;

    @NotNull
    @ManyToOne
    private Person organiser;

    private String eventImage = "defaultBanner.png";

    public Event() {
    }

    public Event(String title, Date date, Address location, String description, Date deadline, eventCategory eventCategory, Person organiser, int maxPax, int min) {
        this.attendanceList = new ArrayList<>();
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.deadline = deadline;
        this.eventCategory = eventCategory;
        this.organiser = organiser;
        this.maxPax = maxPax;
        this.estimateDurationMins = min;

    }

    public Event(String title, Date date, Address location, String description, Date deadline, eventCategory eventCategory, Person organiser,
            int maxPax, int min, String imgName) {
        this.attendanceList = new ArrayList<>();
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.deadline = deadline;
        this.eventCategory = eventCategory;
        this.organiser = organiser;
        this.maxPax = maxPax;
        this.estimateDurationMins = min;
        this.eventImage = imgName;

    }

    public Date getEventEndTime() {
        GregorianCalendar endTimeCalendar = new GregorianCalendar();
        endTimeCalendar.setTime(date);
        endTimeCalendar.add(GregorianCalendar.MINUTE, getEstimateDurationMins());
        return endTimeCalendar.getTime();
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public int getEstimateDurationMins() {
        return estimateDurationMins;
    }

    public void setEstimateDurationMins(int estimateDurationMins) {
        this.estimateDurationMins = estimateDurationMins;
    }

    public List<PersonAttendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<PersonAttendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public int getMaxPax() {
        return maxPax;
    }

    public void setMaxPax(int maxPax) {
        this.maxPax = maxPax;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public eventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(eventCategory evenCategory) {
        this.eventCategory = evenCategory;
    }

    public Person getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Person organiser) {
        this.organiser = organiser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Event[ id=" + id + " ]";
    }

//    public Date getArrivalDateTime() {
//        GregorianCalendar arrivalDateTimeCalendar = new GregorianCalendar();
//        arrivalDateTimeCalendar.setTime(departureDateTime);
//        arrivalDateTimeCalendar.add(GregorianCalendar.MINUTE, getEstimateDurationMins());
//        return arrivalDateTimeCalendar.getTime();
//    }
}
