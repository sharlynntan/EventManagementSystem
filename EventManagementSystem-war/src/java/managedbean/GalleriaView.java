/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.ResponsiveOption;

/**
 *
 * @author Sharlynn
 */
@Named(value = "galleriaView")
@RequestScoped
public class GalleriaView {

    private List<String> photos;

    private List<ResponsiveOption> responsiveOptions;

    public GalleriaView() {
    }

    @PostConstruct
    public void init() {
        System.out.println("testets");
        photos = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            photos.add("gallery" + i + ".png");
        }

        responsiveOptions = new ArrayList<>();
        responsiveOptions.add(new ResponsiveOption("1024px", 5));
        responsiveOptions.add(new ResponsiveOption("768px", 3));
        responsiveOptions.add(new ResponsiveOption("560px", 1));
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<ResponsiveOption> getResponsiveOptions() {
        return responsiveOptions;
    }

    public void setResponsiveOptions(List<ResponsiveOption> responsiveOptions) {
        this.responsiveOptions = responsiveOptions;
    }

}
