package com.SmartHouse.Resources;

import com.SmartHouse.Database.DBConnection;
import com.SmartHouse.Models.User;
import com.SmartHouse.Services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService users = new UserService();


    @GET
    public List<String> getEmails() {
        return users.getAllUsers();
    }


    @POST
    public String addUser(User user) {
        if (!new DBConnection().emailExists(user.getEmailAddress())) {
            if (users.addUser(user))
                return "A new user has been created successfully and the password is 'password'." +
                        " Make sure to update your password after logging in!";
            else
                return "Error! please try again";
        } else
            return "Email address exits! please try again";
    }

    @GET
    @Path("/{emailAddress}/forgotPassword")
    public String sendEmail(@PathParam("emailAddress") String emailAddress) {

        if (new DBConnection().emailExists(emailAddress)) {
            if (users.sendEmail(emailAddress))
                return "An email with a new password has been sent successfully to your email: " + emailAddress;

        }
        return "Invalid email";
    }



}
