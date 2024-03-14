/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import ejb.session.stateless.EventSessionBeanLocal;
import entity.Event;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author sharl
 */
@Named(value = "searchBean")
@RequestScoped
public class SearchBean {

    @EJB
    private EventSessionBeanLocal eventSessionBeanLocal;

    private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    private Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();

    private String userQuery = requestParameterMap.get("searchQuery");

    private String eventEnum = requestParameterMap.get("enumString");

    private String searchQuery = null;

    private List<Event> filteredEvent;

    private String enumString = null;

    public SearchBean() {
    }

    @PostConstruct
    public void populateSearchResult() {
        filteredEvent = eventSessionBeanLocal.getAllEvents();

        if (userQuery != null) {
            filteredEvent = eventSessionBeanLocal.getFilteredEvent(userQuery);
        }

        if (eventEnum != null) {
            filteredEvent = eventSessionBeanLocal.getCategorisedEvent(eventEnum);
        }

    }

    public String search() {
        // Process search query and redirect to search page
        return "searchResult.xhtml?faces-redirect=true&searchQuery=" + searchQuery;
    }

    public String categorySearch(String str) {
        setEnumString(str);
        return "searchResult.xhtml?faces-redirect=true&enumString=" + enumString;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public List<Event> getFilteredEvent() {
        return filteredEvent;
    }

    public void setFilteredEvent(List<Event> filteredEvent) {
        this.filteredEvent = filteredEvent;
    }

    public String getEventEnum() {
        return eventEnum;
    }

    public void setEventEnum(String eventEnum) {
        this.eventEnum = eventEnum;
    }

    public String getEnumString() {
        return enumString;
    }

    public void setEnumString(String enumString) {
        this.enumString = enumString;
    }

}
