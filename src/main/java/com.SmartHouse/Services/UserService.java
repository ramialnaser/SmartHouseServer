package com.SmartHouse.Services;

import com.SmartHouse.Database.DBConnection;
import com.SmartHouse.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;


    public UserService() {
        users = new DBConnection().getUsers();
    }

    public List<String> getAllUsers() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            emails.add(users.get(i).getEmailAddress());
        }

        return emails;
    }

    public User getUserDetails(String emailAddress) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailAddress().equalsIgnoreCase(emailAddress)) {
                return users.get(i);
            }
        }
        return null;
    }

    public boolean addUser(User user) {

        if (new DBConnection().addUser(user, "password"))
            return true;
        else
            return false;
    }

    public boolean updatePassword(String emailAddress, String newPassword) {

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailAddress().equalsIgnoreCase(emailAddress)) {
                if (new DBConnection().updatePassword(emailAddress, newPassword))
                    return true;

            }
        }
        return false;
    }


    public boolean sendEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailAddress().equalsIgnoreCase(email)) {
                if (new DBConnection().sendEmail(email))
                    return true;

            }
        }
        return false;
    }
    public boolean deleteUser(String email){
        for (int i =0; i<users.size(); i++){
            if (users.get(i).getEmailAddress().equalsIgnoreCase(email)){
                users.remove(i);
                return new DBConnection().deleteUser(email);
            }
        }
        return false;
    }
}
