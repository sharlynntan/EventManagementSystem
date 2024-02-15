/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import entity.Person;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import session.PersonSessionBeanLocal;

/**
 *
 * @author Sharlynn
 */
@Named(value = "personManagedBean")
@RequestScoped
public class PersonManagedBean {

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

    private String firstName;

    private String lastName;

    private String contactNumber;

    private String email;

    private String password;

    private String confirmedPassword;

    private boolean passwordChecked = false;

    public PersonManagedBean() {
    }

//    public void validatePassword(FacesContext facesContext, UIComponent uIComponent, Object object) {
//        if (password != null) {
//            if (confirmedPassword == null || !confirmedPassword.equals(password)) {
//                facesContext.addMessage(uIComponent.getClientId(facesContext), new FacesMessage(
//                        "Password does not match"));
//            } else {
//                passwordChecked = true;
//            }
//        }
//    }

    public void addPerson(ActionEvent evt) {
        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setContactNumber(contactNumber);
        p.setEmail(email);
        p.setPassword(password);
        System.out.println("did it come here");
        personSessionBeanLocal.createPerson(p);

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public boolean isPasswordChecked() {
        return passwordChecked;
    }

    public void setPasswordChecked(boolean passwordChecked) {
        this.passwordChecked = passwordChecked;
    }

}
