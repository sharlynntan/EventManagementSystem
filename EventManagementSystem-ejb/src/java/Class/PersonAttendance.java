package Class;

import entity.Person;
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sharlynn
 */
public class PersonAttendance implements Serializable{
    private Person person;
    private boolean attendance;

    public PersonAttendance() {
    }

    public PersonAttendance(Person person) {
        this.person = person;
        attendance = false;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }
    
    
    
    
    
}
