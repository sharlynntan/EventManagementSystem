//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Map;
//import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
//import javax.faces.context.FacesContext;
//import javax.servlet.ServletContext;
//import org.primefaces.model.file.UploadedFile;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.ExternalContext;
//
//@RequestScoped
//@Named("imageBean")
//public class ImageBean {
//
//    private UploadedFile uploadedfile;
//    private String filename = "";
////    private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
////    private Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
//
//    public ImageBean() {
//    }
//
//    public void upload() throws IOException {
//        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//
//        //get the deployment path
//        String UPLOAD_DIRECTORY = ctx.getRealPath("/") + "profilePicture/";
//        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
//
//        //debug purposes
////        setFilename(Paths.get(uploadedfile).getFileName().toString());
////        System.out.println("filename: " + getFilename());
//        //---------------------
//        //replace existing file
//        Path path = Paths.get(UPLOAD_DIRECTORY + getFilename());
//        InputStream bytes = uploadedfile.getInputStream();
////        System.out.println("adsafafafafsafsfafasfdsfafas" + bytes.toString());
//        Files.copy(bytes, path, StandardCopyOption.REPLACE_EXISTING);
//        //        if (uploadedfile != null) {
//        //            FacesMessage message = new FacesMessage("Successful", uploadedfile.getFileName() + " is uploaded.");
//        //            FacesContext.getCurrentInstance().addMessage(null, message);
//
//        //        }
//    }
//
//    public UploadedFile getUploadedfile() {
//        return uploadedfile;
//    }
//
//    public void setUploadedfile(UploadedFile uploadedfile) {
//        this.uploadedfile = uploadedfile;
//    }
//
//    public String getFilename() {
//        return filename;
//    }
//
//    public void setFilename(String filename) {
//        this.filename = filename;
//    }
//
//}
