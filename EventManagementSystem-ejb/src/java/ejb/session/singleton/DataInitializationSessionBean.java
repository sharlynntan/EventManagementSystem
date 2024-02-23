/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import Class.Address;
import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.PersonSessionBeanLocal;
import entity.Event;
import entity.Person;
import util.enumeration.eventCategory;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author Sharlynn
 */
@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

    @EJB
    private EventSessionBeanLocal eventSessionBeanLocal;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            Person p = personSessionBeanLocal.getPerson("dukelee@gmail.com");
        } catch (Exception ex) {
            dataInit();
        }
    }

    private void dataInit() {
        try {
            // Person(String firstName, String lastName, String contactNumber, String email, String password)
            personSessionBeanLocal.createPerson(new Person("Duke", "Lee", "91111111", "dukelee@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Cindy", "Soo", "92222222", "cindysoo@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Ma", "Tee", "98777777", "matee@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Sya", "Fi", "96565666", "syafi@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Vri", "Sin", "98232433", "vrisin@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Happy", "Here", "67223333", "happyhere@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Milk", "Mama", "61010101", "milkmama@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Uniqlo", "PteLtd", "60000000", "uniqlopl@gmail.com", "password"));
            personSessionBeanLocal.createPerson(new Person("Koi", "The", "92743823", "koithe@gmail.com", "password"));

            Person p1 = personSessionBeanLocal.getPerson("dukelee@gmail.com");
            Person p2 = personSessionBeanLocal.getPerson("cindysoo@gmail.com");
            Person p3 = personSessionBeanLocal.getPerson("syafi@gmail.com");
            Person p4 = personSessionBeanLocal.getPerson("koithe@gmail.com");
            Person p5 = personSessionBeanLocal.getPerson("uniqlopl@gmail.com");

            // Address(String street1, String street2, String city, String postalCode)
            Address a1 = new Address("Kent Ridge MRT", "Kent Ridge Drive", "Kent Ridge", "S123456");
            Address a2 = new Address("Marina Barrage", "Mariana Drve", "Marina Bay", "S123098");
            Address a3 = new Address("Ion Orchard", "Orchard Road", "Orchard", "S123678");
            Address a4 = new Address("National University of Singapore", "Kent Ridge Road", "Kent Ridge", "S556765");
            Address a5 = new Address("National Stadium", "Balestier Road", "Balestier Road", "S132098");
            Address a6 = new Address("Changi Jewel", "Changi Airport", "Changi", "S143232");

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            //Event(String title, Date date, Address location, String description, Date deadline, eventCategory eventCategory, Person organiser, maxpax)
            Event e1 = new Event("Dog Day", df.parse("10/04/2024"), a1, "Are you excited for our annual dog day. Come join us today! Bring your dog along!!",
                    df.parse("09/04/2024"), eventCategory.HOBBIES, p1, 100);
//            Event e1 = new Event();
//            e1.setTitle("Dog Day");
//            e1.setDate(df.parse("10/04/2024"));
//            e1.setLocation(a1);
//            e1.setDescription("Are you excited for our annual dog day. Come join us today! Bring your dog along!!");
//            e1.setDeadline(df.parse("09/04/2024"));
//            e1.setEventCategory(eventCategory.HOBBIES);
//            e1.setOrganiser(p1);
//            e1.setMaxPax(100);
//            eventSessionBeanLocal.createEvent(e1);
            p1.getListOfEvent().add(e1);
            Event e2 = new Event("University Experience Convention", df.parse("10/06/2024"), a4, "Are you a final year student? Would you like to find more about campus life? Come join us now",
                    df.parse("09/06/2024"), eventCategory.OTHER, p2,200);
            eventSessionBeanLocal.createEvent(e2);
            p2.getListOfEvent().add(e2);
            eventSessionBeanLocal.createEvent(new Event("Consulting Cofee Chat", df.parse("30/04/2024"), a6, "Ready to expand your carreer to the next level? Come join us today",
                    df.parse("29/04/2024"), eventCategory.BUSINESS, p3,100));
            eventSessionBeanLocal.createEvent(new Event("Baking Roadshow", df.parse("10/05/2024"), a1, "Passionate about baking? Come join us now at the baking roadshow to find out more about classes",
                    df.parse("09/05/2024"), eventCategory.HOBBIES, p1,140));
            eventSessionBeanLocal.createEvent(new Event("Science Centre Adventure", df.parse("01/06/2024"), a5, "School holidays are coming, nothing to do this holidy? Join us today! Parents bring children along for free.",
                    df.parse("20/5/2024"), eventCategory.HOLIDAYS, p4,200));
            eventSessionBeanLocal.createEvent(new Event("Wellbeing day", df.parse("12/04/2024"), a6, "Feeling stress about work? Come join us today at wellbeing day. Free health screening and talks from professional fields.",
                    df.parse("11/04/2024"), eventCategory.HEALTH, p5,102));
            eventSessionBeanLocal.createEvent(new Event("Koi Taste Testing", df.parse("10/07/2024"), a3, "Food tasting for all!",
                    df.parse("09/07/2024"), eventCategory.FOODANDDRINK, p5,200));
            eventSessionBeanLocal.createEvent(new Event("Fitness Event", df.parse("10/04/2024"), a1, "Annual fitness event for all",
                    df.parse("09/04/2024"), eventCategory.HOBBIES, p3,500));
            eventSessionBeanLocal.createEvent(new Event("University Performing Showcase", df.parse("10/04/2024"), a1, "Come catch the annual showcase day for everyone",
                    df.parse("09/04/2024"), eventCategory.HOBBIES, p2,300));
            eventSessionBeanLocal.createEvent(new Event("Hari Raya Bazaar", df.parse("10/04/2024"), a1, "Are you excited for our annual bazzar? Come join us for all the delicious food",
                    df.parse("09/04/2024"), eventCategory.FOODANDDRINK, p1,400));
            eventSessionBeanLocal.createEvent(new Event("Job Fair 2024", df.parse("10/04/2024"), a1, "Singapore biggest workfair is back by popular demand!",
                    df.parse("09/04/2024"), eventCategory.BUSINESS, p3,300));
            
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
