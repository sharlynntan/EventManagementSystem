/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import ejb.session.stateless.PersonSessionBeanLocal;
import entity.Person;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;
import util.exception.NoResultException;

/**
 *
 * @author Sharlynn
 */
@Named(value = "fileUploadView")
@RequestScoped
public class FileUploadView {

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

    private UploadedFile file;
//    private UploadedFiles files;
//    private String dropZoneText = "Drop zone p:inputTextarea demo.";

    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    private final String destination = "web/profilePicture/";

    public void uploadProfilePicture() throws IOException {
        if (file != null) {
            try {
                System.out.println(file.getFileName());
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            FacesContext.getCurrentInstance().addMessage(null, message);
                String UPLOAD_DIRECTORY = ctx.getRealPath("/") + "profilePicture/";
                System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
                Path path = Paths.get(UPLOAD_DIRECTORY + file.getFileName());
                InputStream bytes = file.getInputStream();
                Files.copy(bytes, path, StandardCopyOption.REPLACE_EXISTING);
                Person p = personSessionBeanLocal.getPerson(authenticationManagedBean.getUserId());
                p.setProfilePictureName(file.getFileName());
                personSessionBeanLocal.updatePerson(p);
                FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            } catch (NoResultException ex) {
                FacesMessage message = new FacesMessage("Unsuccessful", file.getFileName() + " is not uploaded.");
            }

        }
    }
    
    public void uploadEventPicture() throws IOException {
        if (file != null) {
            try {
                System.out.println(file.getFileName());
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            FacesContext.getCurrentInstance().addMessage(null, message);
                String UPLOAD_DIRECTORY = ctx.getRealPath("/") + "eventPicture/";
                System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
                Path path = Paths.get(UPLOAD_DIRECTORY + file.getFileName());
                InputStream bytes = file.getInputStream();
                Files.copy(bytes, path, StandardCopyOption.REPLACE_EXISTING);
                Person p = personSessionBeanLocal.getPerson(authenticationManagedBean.getUserId());
                p.setProfilePictureName(file.getFileName());
                personSessionBeanLocal.updatePerson(p);
                FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            } catch (NoResultException ex) {
                FacesMessage message = new FacesMessage("Unsuccessful", file.getFileName() + " is not uploaded.");
            }

        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
