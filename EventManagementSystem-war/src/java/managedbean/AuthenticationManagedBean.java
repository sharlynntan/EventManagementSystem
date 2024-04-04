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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private String name = "";

    /**
     * Creates a new instance of AuthenticationManagedBean
     */
    public AuthenticationManagedBean() {
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println(email);
        System.out.println(password);

        try {
            Person p = personSessionBeanLocal.getPerson(email);
            System.out.println(p.getPassword());
            String hashedPassword = hashPassword(password);
//            System.out.println(hashedPassword);
//            System.out.println(p.getPassword());
            if (p.getPassword().equals(hashedPassword)) {
                userId = p.getId();
                loggedIn = true;
                name = p.getFirstName();
                return "secret/mainMenu.xhtml?faces-redirect=true";
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
//        System.out.println("trying to logout didi");
        return "/index.xhtml?faces-redirect=true";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
