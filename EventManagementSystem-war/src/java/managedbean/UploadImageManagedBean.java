/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import ejb.session.stateless.PersonSessionBeanLocal;
import entity.Person;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.servlet.http.Part;
import util.exception.NoResultException;

/**
 *
 * @author sharl
 */
@Named(value = "uploadImageManagedBean")
@RequestScoped
public class UploadImageManagedBean {

    private Part uploadedFile; // +getter+setter
    private File savedFile;
    private String filename = "";
    private String uploadDirectory = "web/profilePicture/";

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    /**
     * Creates a new instance of UploadImageManagedBean
     */
    public UploadImageManagedBean() {
    }

//    public void upload() throws IOException {
//        if (uploadedFile == null) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No file selected for upload."));
//            return;
//        }
//        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//
//        String fileName = Paths.get(uploadedFile.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
//        Path savedFilePath = Paths.get(uploadDirectory, fileName);
//        System.out.println(fileName);
//
//        String UPLOAD_DIRECTORY = ctx.getRealPath("/") + "profilePicture/";
//        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
//        Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
//        InputStream bytes = savedFile.getInputStream();
//        Files.copy(bytes, path, StandardCopyOption.REPLACE_EXISTING);
//
//        try (InputStream input = uploadedFile.getInputStream()) {
//            Files.copy(input, savedFilePath);
//            Person p = personSessionBeanLocal.getPerson(authenticationManagedBean.getUserId());
//            p.setProfilePictureName(fileName);
//            personSessionBeanLocal.updatePerson(p);
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "File uploaded successfully."));
//        } catch (IOException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to upload file."));
//            e.printStackTrace(); // Log the exception
//        } catch (NoResultException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to find user."));
//        }
//    }.
    public void upload() {
        if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No file selected for upload."));
            return;
        }

        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String UPLOAD_DIRECTORY = ctx.getRealPath("/") + "profilePicture/";

        String fileName = Paths.get(uploadedFile.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        File savedFile = new File(UPLOAD_DIRECTORY, fileName);

        try (InputStream input = uploadedFile.getInputStream();
                OutputStream output = new FileOutputStream(savedFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            Person p = personSessionBeanLocal.getPerson(authenticationManagedBean.getUserId());
            p.setProfilePictureName(fileName);
            personSessionBeanLocal.updatePerson(p);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "File uploaded successfully."));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to upload file."));
            e.printStackTrace(); // Log the exception
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to find user."));
        }
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public File getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(File savedFile) {
        this.savedFile = savedFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUploadDirectory() {
        return uploadDirectory;
    }

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

}
