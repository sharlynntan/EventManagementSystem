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
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import ejb.session.stateless.PersonSessionBeanLocal;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.context.ExternalContext;
import util.exception.NoResultException;

/**
 *
 * @author Sharlynn
 */
@Named(value = "personManagedBean")
@ViewScoped
public class PersonManagedBean implements Serializable {

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

//    @ManagedProperty(value = "#{authenticationManagedBean}")
    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    public AuthenticationManagedBean getAuthenticationManagedBean() {
        return authenticationManagedBean;
    }

    public void setAuthenticationManagedBean(AuthenticationManagedBean authenticationManagedBean) {
        this.authenticationManagedBean = authenticationManagedBean;
    }

    private String firstName;

    private String lastName;

    private String contactNumber;

    private String email;

    private String password;

    private String confirmedPassword;

    private boolean passwordChecked = false;

    private Person selectedPerson;

    private Long pId;

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
    public void addPerson() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (checkAccount()) {
                Person p = new Person();
                p.setFirstName(firstName);
                p.setLastName(lastName);
                p.setContactNumber(contactNumber);
                p.setEmail(email);
                p.setPassword(password);
                personSessionBeanLocal.createPerson(p);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("login.xhtml");
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "You have an account already!!"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private boolean checkAccount() {
        try {
            Person checkEmail = personSessionBeanLocal.getPerson(email);
            return false;
        } catch (NoResultException ex) {
            return true;
        }

    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();
            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public void loadSelectedPerson() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            pId = authenticationManagedBean.getUserId();
//            System.out.println("testting"  + pId);
            this.selectedPerson = personSessionBeanLocal.getPerson(pId);
            firstName = selectedPerson.getFirstName();
            lastName = selectedPerson.getLastName();
            contactNumber = selectedPerson.getContactNumber();
            email = selectedPerson.getEmail();

        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load customer"));
        }
    }

    public void updatePerson() {
        FacesContext context = FacesContext.getCurrentInstance();
        selectedPerson.setFirstName(firstName);
        selectedPerson.setLastName(lastName);
        selectedPerson.setContactNumber(contactNumber);
        try {
            personSessionBeanLocal.updatePerson(selectedPerson);

        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update customer"));
        }

        loadSelectedPerson();

        context.addMessage(null, new FacesMessage("Success",
                "Successfully updated customer"));

    }

    public Person getPersonWithId(long id) throws Exception {
        try {
            return personSessionBeanLocal.getPerson(id);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String retrieveProfilePictureLink() throws NoResultException {
        try {
            return personSessionBeanLocal.getPerson(authenticationManagedBean.getUserId()).getProfilePictureName();
        } catch (NoResultException ex) {
            throw ex;
        }
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

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

}
