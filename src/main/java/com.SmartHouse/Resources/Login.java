package com.SmartHouse.Resources;

import com.SmartHouse.Models.User;
import com.SmartHouse.Services.UserService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Login {

    private UserService users = new UserService();


    @GET
    @Path("/{emailAddress}")
    public User getCustomer(@PathParam("emailAddress") String emailAddress) {
        return users.getUserDetails(emailAddress);
    }


    @PUT
    @Path("/{emailAddress}")
    public String updatePassword(@PathParam("emailAddress") String emailAddress, String password) {
        if (password != null) {
            if (users.updatePassword(emailAddress, password)) {
                return "Password is successfully updated";
            } else
                return "Invalid password! please try again";
        } else
            return "Password cannot be null";
    }

    @DELETE
    @Path("/{emailAddress}")
    public String deleteUser(@PathParam("emailAddress") String emailAddress) {
        if (users.deleteUser(emailAddress)) {
            return "User " + emailAddress + " has been deleted successfully";
        } else {
            return "Failed to delete";
        }
    }


}
