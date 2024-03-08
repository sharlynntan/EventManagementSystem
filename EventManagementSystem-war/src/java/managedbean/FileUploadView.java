/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;

/**
 *
 * @author Sharlynn
 */
@Named(value = "fileUploadView")
@RequestScoped
public class FileUploadView {

    private UploadedFile file;
//    private UploadedFiles files;
//    private String dropZoneText = "Drop zone p:inputTextarea demo.";

    private final String destination = "web/profilePicture/";

    public void upload() throws IOException {
        if (file != null) {

            System.out.println(file.getFileName());
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            FacesContext.getCurrentInstance().addMessage(null, message);
            String UPLOAD_DIRECTORY = ctx.getRealPath("/") + "profilePicture/";
            System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
            Path path = Paths.get(UPLOAD_DIRECTORY + file.getFileName());
            InputStream bytes = file.getInputStream();
            Files.copy(bytes, path, StandardCopyOption.REPLACE_EXISTING);
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
//            handleFileUpload();
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");

        try {
            String fileName = event.getFile().getFileName();
            InputStream inputStream = event.getFile().getInputStream();
            OutputStream outputStream = new FileOutputStream(new File(destination + fileName));

            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            inputStream.close();
            outputStream.close();
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            message = new FacesMessage("Error", event.getFile().getFileName() + " could not be uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void handleFileUploadTextarea(FileUploadEvent event) {
        String jsVal = "PF('textarea').jq.val";
        String fileName = EscapeUtils.forJavaScript(event.getFile().getFileName());
        PrimeFaces.current().executeScript(jsVal + "(" + jsVal + "() + '\\n\\n" + fileName + " uploaded.')");
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
