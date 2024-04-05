package webservices.restful;

import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.PersonSessionBeanLocal;
import entity.Credentials;
import entity.Person;
import io.jsonwebtoken.Jwts;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import managedbean.AuthenticationManagedBean;

@Path("/authentication")
@SessionScoped
public class AuthenticationEndpointResource implements Serializable{

    // 5 mins token expiry (can set this to be much larger value)
    final int TOKEN_EXPIRY = 100;

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;
    
    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Person person) {
        JsonObject LOGIN_FAILED_ERROR = Json.createObjectBuilder()
                .add("error", "Login Failed")
                .build();

        try {
            //TODO: perform authentication
            //here we will do a fake authentication
            
            System.out.println(person.getPassword());
            String hashedPassword = hashPassword(person.getPassword());
            person.setPassword(hashedPassword);
            
            Person p = personSessionBeanLocal.getPerson(person.getEmail());
            

            if (p.getPassword().equals(hashedPassword)) {

                //assume that the userId of this person is 51
                long userId = p.getId();
                authenticationManagedBean.setUserId(userId);
                authenticationManagedBean.setEmail(p.getEmail());
                authenticationManagedBean.setLoggedIn(true);
                authenticationManagedBean.setName(p.getFirstName());
                authenticationManagedBean.setPassword(hashedPassword);
                
                Date now = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(now);
                cal.add(Calendar.MINUTE, TOKEN_EXPIRY);

                String jwtToken = Jwts.builder()
                        .claim("userId", "" + userId) //store the userId as a String
                        .setId(UUID.randomUUID().toString())
                        .setIssuedAt(now)
                        .setExpiration(cal.getTime())
                        .signWith(JWTHelper.hmacKey)
                        .compact();

                System.out.println("#jwtToken: " + jwtToken);
                
                JsonObject token = Json.createObjectBuilder()
                        .add("token", jwtToken)
                        .build();
                return Response.status(Response.Status.OK).entity(token).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(LOGIN_FAILED_ERROR).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(LOGIN_FAILED_ERROR).build();
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
    
    public AuthenticationManagedBean getAuthenticationManagedBean() {
        return authenticationManagedBean;
    }

    public void setAuthenticationManagedBean(AuthenticationManagedBean authenticationManagedBean) {
        this.authenticationManagedBean = authenticationManagedBean;
    }
}
