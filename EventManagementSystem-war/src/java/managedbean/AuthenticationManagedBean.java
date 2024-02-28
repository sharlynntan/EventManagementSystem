/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import entity.Person;
import util.exception.NoResultException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import ejb.session.stateless.PersonSessionBeanLocal;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Sharlynn
 */
@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

    private String email = null;
    private String password = null;
    private Long userId = -1L;
    private boolean loggedIn = false;

    /**
     * Creates a new instance of AuthenticationManagedBean
     */
    public AuthenticationManagedBean() {
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Person p = personSessionBeanLocal.getPerson(email);
            System.out.println(p.getPassword());
            if (p.getPassword().equals(password)) {
                userId = p.getId();
                loggedIn = true;
                return "mainMenu.xhtml?faces-redirect=true";
            } else {
                email = null;
                password = null;
                userId = -1L;
                loggedIn = false;
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to login"));
                return "login.xhtml";
            }

        } catch (NoResultException ex) {
            email = null;
            password = null;
            userId = -1L;
            loggedIn = false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to login"));
            return "login.xhtml";
        }
    }

    public String logout() {
        email = null;
        password = null;
        userId = -1L;
        loggedIn = false;
        return "index.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userID) {
        this.userId = userID;
    }

}
